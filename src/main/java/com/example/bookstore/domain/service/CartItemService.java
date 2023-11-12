package com.example.bookstore.domain.service;

import com.example.bookstore.data.models.CartItem;

import java.util.List;

public interface CartItemService {
    List<CartItem> getByTransactionId(String id);
}
