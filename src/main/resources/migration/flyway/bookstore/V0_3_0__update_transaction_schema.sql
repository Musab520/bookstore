USE bookstore;

DROP TABLE transaction_book;


-- Create the cart_item table
CREATE TABLE cart_item
(
    id VARCHAR(255) PRIMARY KEY,
    book_id VARCHAR(255),
    transaction_id VARCHAR(255),
    price DOUBLE,
    count INT,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (transaction_id) REFERENCES transaction(id)
);
