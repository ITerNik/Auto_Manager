---------------------------
-- TABLES INITIALIZATION --
---------------------------

CREATE TABLE IF NOT EXISTS role (
    role VARCHAR(16) PRIMARY KEY CHECK (role IN ('ADMIN', 'USER', 'GUEST', 'MARKETER'))
);

CREATE TABLE IF NOT EXISTS client (
    id UUID PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    role VARCHAR(16) NOT NULL REFERENCES role(role),
    birthday DATE,
    is_blocked BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS car (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES client(id),
    make VARCHAR(32) NOT NULL,
    model VARCHAR(32) NOT NULL,
    year INT NOT NULL,
    transmission VARCHAR(32),
    fuel_type VARCHAR(32),
    class VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS service_notification (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES client(id),
    service_type VARCHAR(32) NOT NULL CHECK (service_type IN ('MAINTENANCE', 'WASH', 'TIRE_CHANGE', 'OIL_CHANGE')),
    notification_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comment TEXT
);

CREATE TABLE IF NOT EXISTS address (
    id UUID PRIMARY KEY,
    city VARCHAR(32) NOT NULL,
    street VARCHAR(32) NOT NULL,
    house VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS place (
    id UUID PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    address_id UUID NOT NULL REFERENCES address(id),
    type VARCHAR(32) NOT NULL CHECK (type IN ('GAS_STATION', 'ELECTRIC_REFUELING', 'CAR_WASH', 'CAR_SERVICE')),
    contact_info TEXT
);

CREATE TABLE IF NOT EXISTS service (
    id UUID PRIMARY KEY,
    place_id UUID NOT NULL REFERENCES place(id),
    service_type VARCHAR(32) NOT NULL CHECK (service_type IN ('MAINTENANCE', 'WASH', 'TIRE_CHANGE', 'OIL_CHANGE')),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_favorite_service (
    user_id UUID NOT NULL REFERENCES client(id),
    service_id UUID NOT NULL REFERENCES service(id),
    PRIMARY KEY (user_id, service_id)
);

CREATE TABLE IF NOT EXISTS review (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES client(id),
    service_id UUID NOT NULL REFERENCES service(id),
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    status VARCHAR(32) CHECK (status IN ('PENDING', 'PUBLISHED', 'ARCHIVED')),
    rejection_reason TEXT
);

CREATE TABLE IF NOT EXISTS recommendation (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES client(id),
    car_id UUID NOT NULL REFERENCES car(id),
    text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    status VARCHAR(32) CHECK (status IN ('PENDING', 'PUBLISHED', 'ARCHIVED')),
    rejection_reason TEXT
);

CREATE TABLE IF NOT EXISTS promotion (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_by UUID NOT NULL REFERENCES client(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_archived BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS promotion_user (
    promotion_id UUID NOT NULL REFERENCES promotion(id),
    user_id UUID NOT NULL REFERENCES client(id),
    end_date TIMESTAMP NOT NULL,
    qr_code TEXT,
    PRIMARY KEY (promotion_id, user_id)
);

CREATE TABLE IF NOT EXISTS inaccuracy (
    id UUID PRIMARY KEY,
    reporter_id UUID NOT NULL REFERENCES client(id),
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved BOOLEAN DEFAULT FALSE
);

----------------
-- PROCEDURES --
----------------

CREATE OR REPLACE PROCEDURE update_review_status(
    p_review_id UUID,
    p_status VARCHAR,
    p_rejection_reason TEXT DEFAULT NULL
)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE review
    SET status = p_status,
        rejection_reason = p_rejection_reason,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_review_id;
END;
$$;


CREATE OR REPLACE FUNCTION assign_promotion_to_user(
    p_promotion_id UUID,
    p_user_id UUID,
    p_end_date TIMESTAMP,
    p_qr_code TEXT
) RETURNS VOID AS $$
BEGIN
    INSERT INTO promotion_user (promotion_id, user_id, end_date, qr_code)
    VALUES (p_promotion_id, p_user_id, p_end_date, p_qr_code);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE resolve_inaccuracy(
    p_inaccuracy_id UUID
)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE inaccuracy
    SET resolved = TRUE
    WHERE id = p_inaccuracy_id;
END;
$$;

--------------------
-- START TRIGGERS --
--------------------

CREATE OR REPLACE FUNCTION hash_password_before_insert()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.password := crypt(NEW.password, gen_salt('bf')); -- Хешируем пароль с использованием Blowfish (bcrypt)
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_client
    BEFORE INSERT ON client
    FOR EACH ROW
EXECUTE FUNCTION hash_password_before_insert();


CREATE OR REPLACE FUNCTION update_review_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_update_review
    BEFORE UPDATE ON review
    FOR EACH ROW
EXECUTE FUNCTION update_review_timestamp();

CREATE OR REPLACE FUNCTION prevent_client_deletion()
    RETURNS TRIGGER AS $$
DECLARE
    car_count INT;
BEGIN
    SELECT COUNT(*) INTO car_count FROM car WHERE user_id = OLD.id;

    IF car_count > 0 THEN
        RAISE EXCEPTION 'Cannot delete user with active cars';
    END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

----------------------------
-- INDEXES INITIALIZAYION --
----------------------------

CREATE INDEX client_role_idx ON client (role);
CREATE INDEX client_is_blocked_idx ON client (is_blocked);
CREATE INDEX client_name_surname_idx ON client (name, surname);

CREATE INDEX car_make_model_idx ON car (make, model);

CREATE INDEX service_notification_service_type_idx ON service_notification (service_type);
CREATE INDEX service_notification_notification_date_idx ON service_notification (notification_date);

CREATE INDEX place_city_idx ON address (city);
CREATE INDEX place_type_idx ON place (type);

CREATE INDEX inaccuracy_resolved_idx ON inaccuracy (resolved);
CREATE INDEX inaccuracy_created_at_idx ON inaccuracy (created_at);

CREATE INDEX address_city_street_house_idx ON address (city, street, house);
