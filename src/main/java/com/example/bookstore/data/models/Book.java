package com.example.bookstore.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@Entity
@Table(name = "book")
@org.hibernate.annotations.NamedQueries({
        @NamedQuery(
                name = "searchBookByTitle",
                query = "from Book where title LIKE CONCAT(:searchTerm, '%')"),
        @NamedQuery(
                name = "searchBookByAuthor",
                query = "from Book where author LIKE CONCAT(:searchTerm, '%')")
})
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

    @NotEmpty
    @NotNull
    @Size(max = 255)
    private String author;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    private String publisher;
    private int row;
    private String shelf;

    private double cost;

    private double price;

    private int count;

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, String author, String publisher, int row, String shelf, double cost, double price, int count) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.row = row;
        this.shelf = shelf;
        this.cost = cost;
        this.price = price;
        this.count = count;
    }

    public Book(String id, String title, String author, String publisher, int row, String shelf, double cost, double price, int count) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.row = row;
        this.shelf = shelf;
        this.cost = cost;
        this.price = price;
        this.count = count;
    }
}