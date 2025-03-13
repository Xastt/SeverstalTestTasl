CREATE TABLE supplier (
    supplier_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_name VARCHAR(100) UNIQUE NOT NULL,
    work_email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE deliver (
    delivery_id UUID PRIMARY KEY,
    supplier_id UUID REFERENCES supplier(supplier_id),
    delivery_date TIMESTAMP NOT NULL,
    weight NUMERIC(8, 2) NOT NULL,
    price_for_kg NUMERIC(8, 2) NOT NULL
);

CREATE INDEX idx_deliver_supplier_id ON deliver(supplier_id);
CREATE INDEX idx_deliver_delivery_date ON deliver(delivery_date);

CREATE TABLE Product(
    product_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_name VARCHAR(100) NOT NULL,
    product_type VARCHAR(100) UNIQUE NOT NULL,
    supplier_id UUID REFERENCES supplier(supplier_id),
    delivery_id UUID REFERENCES deliver(delivery_id)
);

CREATE INDEX idx_product_supplier_id ON Product(supplier_id);
CREATE INDEX idx_product_delivery_id ON Product(delivery_id);