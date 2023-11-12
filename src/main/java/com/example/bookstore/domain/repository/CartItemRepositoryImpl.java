package com.example.bookstore.domain.repository;

import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.data.repository.CartItemRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CartItemRepositoryImpl implements CartItemRepository {
    private final SessionFactory sessionFactory;
    @Override
    public void save(List<CartItem> cartItems) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (CartItem cartItem : cartItems) {
                session.save(cartItem);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public List<CartItem> getByTransactionId(String id) {
        try (Session session = sessionFactory.openSession()) {
            Query<CartItem> query = session.createQuery("from CartItem where transaction_id = :id", CartItem.class);
            query.setParameter("id", id);
            return query.list();
        }
    }
}
