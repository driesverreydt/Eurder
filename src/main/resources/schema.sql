CREATE TABLE IF NOT EXISTS items
(
    item_id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC(19,2) CHECK (price > 0),
    amount INT CHECK (amount >= 0)
    );

CREATE TABLE IF NOT EXISTS users
(
    user_id UUID PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    name_first_name VARCHAR(50) NOT NULL,
    name_last_name VARCHAR(50) NOT NULL,
    address_street_name VARCHAR(50) NOT NULL,
    address_street_number INT NOT NULL,
    address_postal_code INT NOT NULL,
    address_city_name VARCHAR(50) NOT NULL,
    email_user_name VARCHAR(50) NOT NULL,
    email_domain_name VARCHAR(50) NOT NULL,
    email_extension VARCHAR(50) NOT NULL,
    phone_number_digits VARCHAR(50) NOT NULL,
    phone_number_country VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS orders
(
    order_id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE IF NOT EXISTS item_groups
(
    item_group_id UUID PRIMARY KEY,
    item_id UUID NOT NULL,
    order_id UUID NOT NULL,
    price_snapshot NUMERIC(19,2) CHECK (price_snapshot > 0),
    amount INT CHECK (amount > 0),
    shipping_date DATE,
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
    );

