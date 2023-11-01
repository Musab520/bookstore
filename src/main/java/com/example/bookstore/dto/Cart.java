package com.example.bookstore.dto;

import com.example.bookstore.data.models.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Cart {
    List<CartItem> cartItems = new ArrayList<>();

    public CartItem getCartItem(Book book) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getBook().equals(book)) {
                return cartItem;
            }
        }
        return null; // Book not found in the cart
    }
}


