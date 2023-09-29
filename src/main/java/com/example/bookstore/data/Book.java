package com.example.bookstore.data;

import lombok.Data;

@Data
public class Book {
    private int Id;
    private String title;
    private String author;
    private int row;
    private String shelf;
    private double cost;
    private double price;
    private int count;

    public Book(String title) {
        this.title = title;
    }
    public Book(){}
}
