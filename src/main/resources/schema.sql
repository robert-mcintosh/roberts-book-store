CREATE TABLE Book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    price DECIMAL(10, 2),
    type CHAR(3) NOT NULL
);


CREATE TABLE Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    loyalty_points INT NOT NULL
);

CREATE TABLE Purchase (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customerId INT NOT NULL,
    books VARCHAR(255),
    totalPrice DECIMAL(10, 2) NOT NULL
);


CREATE TABLE Discount (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bookType CHAR(3) NOT NULL,
    discountPercentage DECIMAL(10, 2) NOT NULL,
    bundleDiscountPercentage DECIMAL(10, 2) NOT NULL,
)