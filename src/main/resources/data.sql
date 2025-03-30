
INSERT INTO book (title, author, qty, price, type)
VALUES ('Game Theory', 'Brian Clegg', 10, 250.0, 'NEW');
INSERT INTO book (title, author, qty, price, type) VALUES ('In Too Deep', 'Lee Child', 10, 250.0, 'NEW');
INSERT INTO book (title, author, qty, price, type) VALUES ('The Unicorn Project', 'Gene Kim', 10, 200.0, 'NEW');

INSERT INTO book (title, author, qty, price, type) VALUES ('Throne of Glass', 'Sarah J Maas', 10, 150.0, 'REG');
INSERT INTO book (title, author, qty, price, type) VALUES ('Wheres Wally', 'Martin Handford ', 10, 100.0, 'REG');
INSERT INTO book (title, author, qty, price, type) VALUES ('The Let Them Theory', 'Mel Robbins', 10, 400.0, 'REG');

INSERT INTO book (title, author, qty, price, type) VALUES ('Before the Coffee Gets Cold', 'Toshikazu Kawaguchi', 10, 150.0, 'OLD');
INSERT INTO book (title, author, qty, price, type) VALUES ('The Art of War', 'Thomas Cleary', 10, 150.0, 'OLD');
INSERT INTO book (title, author, qty, price, type) VALUES ('The 48 Laws of Power', 'Robert Greene', 10, 250.0, 'OLD');

INSERT INTO Discount (book_type, description, discount_percentage, bundle_discount_percentage, loyalty_eligible) VALUES ('NEW', 'New Release', 0, 0, 'N');
INSERT INTO Discount (book_type, description, discount_percentage, bundle_discount_percentage, loyalty_eligible) VALUES ('REG', 'Regular', 0, 10, 'Y');
INSERT INTO Discount (book_type, description, discount_percentage, bundle_discount_percentage, loyalty_eligible) VALUES ('OLD', 'Old Edition', 20, 5, 'Y');

INSERT INTO customer (name, loyalty_points) VALUES ('Michael', 0);
INSERT INTO customer (name, loyalty_points) VALUES ('Janet', 0);
INSERT INTO customer (name, loyalty_points) VALUES ('Steven', 0);
INSERT INTO customer (name, loyalty_points) VALUES ('Amy', 0);
