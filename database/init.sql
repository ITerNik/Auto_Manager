CREATE TABLE IF NOT EXISTS role (
    role VARCHAR(16) PRIMARY KEY CHECK (role IN ('ADMIN', 'DRIVER', 'SUPPORT'))
);

CREATE TABLE IF NOT EXISTS client (
   id UUID PRIMARY KEY,
   name VARCHAR(16) NOT NULL,
   surname VARCHAR(16) NOT NULL,
   password VARCHAR(64) NOT NULL,
   email VARCHAR(32) NOT NULL,
   role VARCHAR(32) NOT NULL REFERENCES role(role),
   birthday TIMESTAMP
);

CREATE TABLE IF NOT EXISTS car (
    id UUID PRIMARY KEY,
    city_mpg INT,
    class VARCHAR(32),
    combination_mpg INT,
    cylinders INT,
    displacement DECIMAL,
    drive VARCHAR(32),
    fuel_type VARCHAR(32),
    highway_mpg INT,
    make VARCHAR(32),
    model VARCHAR(32),
    transmission VARCHAR(32),
    year INT
);

CREATE TABLE IF NOT EXISTS user_car (
    user_id UUID NOT NULL REFERENCES client(id),
    car_id UUID NOT NULL REFERENCES car(id),
    PRIMARY KEY (user_id, car_id)
);

CREATE TABLE IF NOT EXISTS service_notifications (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES client(id),
    service_type VARCHAR(32) NOT NULL CHECK (service_type IN ('MAINTENANCE', 'WASH', 'TIRE_CHANGE', 'OIL_CHANGE')),
    date TIMESTAMP NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    comment TEXT
);

CREATE TABLE IF NOT EXISTS support_messages (
    id UUID PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    title VARCHAR(32) NOT NULL,
    comment TEXT
);

CREATE TABLE IF NOT EXISTS user_SM (
    support_message_id UUID NOT NULL REFERENCES support_messages(id),
    user_id UUID NOT NULL REFERENCES client(id),
    PRIMARY KEY (support_message_id, user_id)
);

CREATE TABLE IF NOT EXISTS address (
    id UUID PRIMARY KEY,
    city VARCHAR(32) NOT NULL,
    street VARCHAR(32) NOT NULL,
    house INT NOT NULL CHECK (house > 0),
    building INT CHECK (building > 0)
);

CREATE TABLE IF NOT EXISTS place (
    id UUID PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    address_id UUID NOT NULL REFERENCES address(id),
    type VARCHAR(32) NOT NULL CHECK (type IN ('GAS_STATION', 'ELECTRIC_REFUELING', 'CAR_WASH', 'CAR_SERVICE')),
    address_comment TEXT
);

CREATE TABLE IF NOT EXISTS services (
    id UUID PRIMARY KEY,
    service_type VARCHAR(32) NOT NULL CHECK (service_type IN ('MAINTENANCE', 'WASH', 'TIRE_CHANGE', 'OIL_CHANGE')),
    date TIMESTAMP NOT NULL,
    comment TEXT,
    place_id UUID REFERENCES place(id)
);

CREATE TABLE IF NOT EXISTS user_service (
    service_id UUID NOT NULL REFERENCES services(id),
    user_id UUID NOT NULL REFERENCES client(id),
    PRIMARY KEY (service_id, user_id)
);

INSERT INTO car (id) VALUES ('c915bac0-79a8-4f91-b767-62c2b7a25427');
