package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class Book {
    private String title;
    private String author;
    private int row;
    private String shelf;
    private double cost;
    private double price;

    public Book(String title) {
        this.title = title;
    }

}
