package com.example.bookstore.domain.service;

import com.example.bookstore.data.models.Transaction;

public interface TransactionService {
    void save(Transaction transaction);
    Transaction getById(int id);
}
