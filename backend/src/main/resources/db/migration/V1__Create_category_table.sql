CREATE TABLE category (
                          id VARCHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL UNIQUE,
                          image_name VARCHAR(255) NOT NULL UNIQUE,
                          is_active VARCHAR(20) NOT NULL,
                          created_at TIMESTAMP NOT NULL,
                          update_at TIMESTAMP,

                          CONSTRAINT chk_is_active CHECK (is_active IN ('ACTIVE', 'INACTIVE'))
);

-- Índices
CREATE INDEX idx_category_is_active ON category(is_active);