USE bookstore;

-- Altering the table to change VARCHAR columns to use utf8mb4 character set
ALTER TABLE book
    MODIFY title VARCHAR(255) CHARACTER SET utf8mb4,
    MODIFY author VARCHAR(255) CHARACTER SET utf8mb4,
    MODIFY publisher VARCHAR(255) CHARACTER SET utf8mb4,
    MODIFY shelf VARCHAR(255) CHARACTER SET utf8mb4;