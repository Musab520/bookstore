package com.example.bookstore.domain.repository;

import com.example.bookstore.data.models.Transaction;
import com.example.bookstore.data.repository.TransactionRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TransactionRepositoryImpl implements TransactionRepository{

    private final SessionFactory sessionFactory;
    @Override
    public void save(Transaction transaction) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(transaction);
            session.getTransaction().commit();
        }
    }

    @Override
    public Transaction getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Transaction.class, id);
        }
    }
}
