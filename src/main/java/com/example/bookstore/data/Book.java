package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String author;

    private int row;

    @Size(max = 255)
    private String shelf;

    private double cost;

    private double price;

    private int count;

    @ManyToMany(mappedBy = "books")
    private List<Sale> sales;

    public Book(String title) {
        this.title = title;
    }
}