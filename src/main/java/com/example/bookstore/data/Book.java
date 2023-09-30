package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String author;

    @Size(max = 255)
    private String publisher;
    private int row;

    @Size(max = 255)
    private String shelf;

    private double cost;

    private double price;

    private int count;

    @ManyToMany(mappedBy = "books")
    private List<Transaction> sales;

    public Book(String title) {
        this.title = title;
    }
}