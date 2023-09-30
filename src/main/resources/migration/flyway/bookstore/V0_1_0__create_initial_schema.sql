use bookstore;
CREATE TABLE book(
    id VARCHAR(255),
    title VARCHAR(255),
    author VARCHAR(255),
    publisher VARCHAR(255),
    `row` INT,
    shelf VARCHAR(255),
    cost DOUBLE,
    price DOUBLE,
    count INT);

ALTER TABLE book
    ADD CONSTRAINT
    PRIMARY KEY(id);