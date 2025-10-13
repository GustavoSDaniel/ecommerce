CREATE TABLE product (
                         id VARCHAR(36) PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         description TEXT NOT NULL,
                         price NUMERIC(19, 2) NOT NULL,
                         stock INTEGER NOT NULL,
                         image_name VARCHAR(255) NOT NULL UNIQUE,
                         active_or_inactive VARCHAR(20) NOT NULL,
                         category_id VARCHAR(36),
                         created_at TIMESTAMP NOT NULL,
                         update_at TIMESTAMP,

                         CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id),
                         CONSTRAINT chk_active_or_inactive CHECK (active_or_inactive IN ('ACTIVE', 'INACTIVE')),
                         CONSTRAINT chk_price_positive CHECK (price > 0),
                         CONSTRAINT chk_stock_non_negative CHECK (stock >= 0)
);

-- Índices
CREATE INDEX idx_product_category_id ON product(category_id);
CREATE INDEX idx_product_active_or_inactive ON product(active_or_inactive);
CREATE INDEX idx_product_price ON product(price);
CREATE INDEX idx_product_category_active ON product(category_id, active_or_inactive);
