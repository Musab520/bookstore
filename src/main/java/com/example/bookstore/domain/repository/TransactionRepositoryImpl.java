package com.example.bookstore.domain.repository;

import com.example.bookstore.data.models.Transaction;
import com.example.bookstore.data.repository.TransactionRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<Transaction> list() {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> query = session.createQuery("from Transaction t order by t.datePurchased desc", Transaction.class);
            return query.list();
        }
    }

    @Override
    public List<Transaction> listFromDate(LocalDate localDate) {
        try (Session session = sessionFactory.openSession()) {
            Query<Transaction> query = session.createQuery(
                    "from Transaction t where t.datePurchased >= :startDate and t.datePurchased < :endDate order by t.datePurchased desc",
                    Transaction.class
            );
            query.setParameter("startDate", localDate.atStartOfDay());
            query.setParameter("endDate", localDate.plusDays(1).atStartOfDay()); // Adding one day to include transactions on the given date
            return query.list();
        }
    }
}
