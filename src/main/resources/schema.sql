
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Purchase;
DROP TABLE IF EXISTS Purchase_Item;
DROP TABLE IF EXISTS Discount;

CREATE TABLE Book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    type CHAR(3) NOT NULL,
    qty INT NOT NULL,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Discount (
    book_type CHAR(3) PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    discount_percentage DECIMAL(10, 2) NOT NULL,
    bundle_discount_percentage DECIMAL(10, 2) NOT NULL,
    loyalty_eligible CHAR(1) DEFAULT 'N',
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    loyalty_points INT NOT NULL,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Purchase (
    purchase_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    refunded CHAR(1)  DEFAULT 'N',
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Purchase_Item (
    id INT PRIMARY KEY AUTO_INCREMENT ,
    purchase_id BIGINT,
    book_id BIGINT,
    price DOUBLE,
    discount_price DOUBLE
);
