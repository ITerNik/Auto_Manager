CREATE TABLE IF NOT EXISTS role (
    role VARCHAR(16) PRIMARY KEY CHECK (role IN ('ADMIN', 'USER', 'GUEST', 'MODERATOR', 'MARKETER'))
);

CREATE TABLE IF NOT EXISTS client (
    id UUID PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    role VARCHAR(16) NOT NULL REFERENCES role(role),
    birthday DATE,
    qr_code TEXT,
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
    class VARCHAR(32),
    vin VARCHAR(32) UNIQUE
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS recommendation (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES client(id),
    car_id UUID NOT NULL REFERENCES car(id),
    text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS support_ticket (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES client(id),
    title VARCHAR(64) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS publication (
    id UUID PRIMARY KEY,
    author_id UUID NOT NULL REFERENCES client(id),
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_published BOOLEAN DEFAULT FALSE,
    is_rejected BOOLEAN DEFAULT FALSE,
    rejection_reason TEXT
);

CREATE TABLE IF NOT EXISTS promotion (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    created_by UUID NOT NULL REFERENCES client(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_archived BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS promotion_user (
    promotion_id UUID NOT NULL REFERENCES promotion(id),
    user_id UUID NOT NULL REFERENCES client(id),
    PRIMARY KEY (promotion_id, user_id)
);

CREATE TABLE IF NOT EXISTS inaccuracy (
    id UUID PRIMARY KEY,
    reporter_id UUID NOT NULL REFERENCES client(id),
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved BOOLEAN DEFAULT FALSE
);
