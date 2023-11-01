package com.example.bookstore.dto;

import com.example.bookstore.data.models.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItem {
    Book book;
    int count;

    public CartItem(Book book, int count){
        this.book = book;
        this.count = count;
    }
}
