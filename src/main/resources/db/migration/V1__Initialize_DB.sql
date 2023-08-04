-- Create investor table
CREATE TABLE investor (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    address VARCHAR(200),
    mobile_number VARCHAR(15),
    email_address VARCHAR(100) NOT NULL
);

-- Create product table
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    product_id VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL
);

-- Insert sample data for investors
INSERT INTO investor (name, surname, date_of_birth, address, mobile_number, email_address)
VALUES
    ('John', 'Doe', '1990-01-01', '123 Main St', '1234567890', 'john.doe@example.com'),
    ('Jane', 'Smith', '1985-05-15', '456 Oak Ave', '9876543210', 'jane.smith@example.com');

-- Insert sample data for products
INSERT INTO product (product_id, type, name, balance)
VALUES
    ('RET1', 'RETIREMENT', 'Retirement Fund', 500000),
    ('SAV1', 'SAVINGS', 'Savings Account', 36000);
