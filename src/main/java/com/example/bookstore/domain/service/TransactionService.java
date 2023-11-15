package com.example.bookstore.domain.service;

import com.example.bookstore.data.models.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);
    Transaction getById(int id);
    List<Transaction> list();
    List<Transaction> listFromDate(LocalDate date);
}
