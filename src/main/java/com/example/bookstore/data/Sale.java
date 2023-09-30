package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "salesman_id") // Foreign key constraint for SalesMan attribute
    private SalesMan salesMan;

    @ManyToMany
    @JoinTable(
            name = "sale_book", // Name of the join table
            joinColumns = @JoinColumn(name = "sale_id"), // Foreign key column in the join table
            inverseJoinColumns = @JoinColumn(name = "book_id") // Foreign key column in the Book table
    )
    private List<Book> books;

    private LocalDateTime timestamp = LocalDateTime.now();
}