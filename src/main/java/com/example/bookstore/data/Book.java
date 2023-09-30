package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    private int Id;
    private String title;
    private String author;
    private String publisher;
    private int row;
    private String shelf;
    private double cost;
    private double price;
    private int count;

    public Book(String title) {
        this.title = title;
    }
}
