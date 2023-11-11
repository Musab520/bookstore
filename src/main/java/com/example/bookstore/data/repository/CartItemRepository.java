package com.example.bookstore.data.repository;

import com.example.bookstore.data.models.CartItem;

import java.util.List;

public interface CartItemRepository {
    void save(List<CartItem> cartItems);
}
