package com.example.bookstore.application.service;

import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.data.repository.CartItemRepository;
import com.example.bookstore.domain.service.CartItemService;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    @Override
    public List<CartItem> getByTransactionId(String id) {
        return cartItemRepository.getByTransactionId(id);
    }
}
