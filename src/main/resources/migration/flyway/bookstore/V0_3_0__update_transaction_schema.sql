USE bookstore;

DROP TABLE transaction_book;


-- Create the cart_item table
CREATE TABLE cart_item
(
    id VARCHAR(255) PRIMARY KEY,
    book_id VARCHAR(255),
    price DOUBLE,
    count INT,
    FOREIGN KEY (book_id) REFERENCES book(id)
);

-- Create the transaction_cart_item table
CREATE TABLE transaction_cart_item
(
    id VARCHAR(255) PRIMARY KEY,
    transaction_id VARCHAR(255),
    cart_item_id VARCHAR(255),
    FOREIGN KEY (transaction_id) REFERENCES `transaction` (id),
    FOREIGN KEY (cart_item_id) REFERENCES cart_item (id)
);
