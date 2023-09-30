use bookstore;

CREATE TABLE `transaction`
(
    id VARCHAR(255) PRIMARY KEY,
    date_purchased TIMESTAMP
);

-- Create sale_book join table for many-to-many relationship
CREATE TABLE transaction_book
(
    id VARCHAR(255) PRIMARY KEY,
    transaction_id VARCHAR(255),
    book_id VARCHAR(255),
    FOREIGN KEY (transaction_id) REFERENCES `transaction` (id),
    FOREIGN KEY (book_id) REFERENCES book (id)
);