use bookstore;
-- update book table
alter TABLE book
    ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY,
    Add COLUMN count INT;

-- Create salesman table
CREATE TABLE salesman
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    phone      VARCHAR(255) UNIQUE,
    email      VARCHAR(255) NOT NULL,
    gender     VARCHAR(255),
    birthday   DATE
);

-- Create sale table
CREATE TABLE sale
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    salesman_id INT,
    timestamp   TIMESTAMP,
    FOREIGN KEY (salesman_id) REFERENCES salesman (id)
);

-- Create sale_book join table for many-to-many relationship
CREATE TABLE sale_book
(
    sale_id INT,
    book_id INT,
    PRIMARY KEY (sale_id, book_id),
    FOREIGN KEY (sale_id) REFERENCES sale (id),
    FOREIGN KEY (book_id) REFERENCES book (id)
);