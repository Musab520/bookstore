package com.example.bookstore.data.repository;

import com.example.bookstore.data.models.Transaction;

public interface TransactionRepository {
    void save(Transaction transaction);
    Transaction getById(int id);
}
