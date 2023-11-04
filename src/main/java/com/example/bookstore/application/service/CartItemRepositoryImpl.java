package com.example.bookstore.application.service;

import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.data.repository.CartItemRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
