CREATE TABLE IF NOT EXISTS users
(
    id                   UUID PRIMARY KEY,
    bank_id              VARCHAR(255),
    last_name            VARCHAR(255),
    first_name           VARCHAR(255),
    middle_name          VARCHAR(255),
    birth_date           DATE,
    passport_number      VARCHAR(255) UNIQUE,
    born_place           VARCHAR(255),
    mobile_phone         VARCHAR(255) UNIQUE,
    email                VARCHAR(255) UNIQUE,
    registration_address VARCHAR(255),
    residential_address  VARCHAR(255)
);




