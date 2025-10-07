CREATE TYPE active_or_inactive_status AS ENUM ('ACTIVE', 'INACTIVE');

CREATE TABLE category (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    image_name VARCHAR(255) NOT NULL UNIQUE,
    is_active active_or_inactive_status NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);