INSERT INTO book (title, author, price, type) VALUES ('Book 1', 'Author 1', 100.0, 'NEW');
INSERT INTO book (title, author, price, type) VALUES ('Book 2', 'Author 2', 80.0, 'REG');
INSERT INTO book (title, author, price, type) VALUES ('Book 3', 'Author 3', 60.0, 'OLD');

INSERT INTO discount (book_type, discount_percentage, bundle_discount_percentage) VALUES ('NEW', 0.0, 0.0);
INSERT INTO discount (book_type, discount_percentage, bundle_discount_percentage) VALUES ('REG', 0.0, 10.0);
INSERT INTO discount (book_type, discount_percentage, bundle_discount_percentage) VALUES ('OLD', 20.0, 5.0);

INSERT INTO customer (name, loyalty_points) VALUES ('John', 0);
INSERT INTO customer (name, loyalty_points) VALUES ('Jane', 5);
