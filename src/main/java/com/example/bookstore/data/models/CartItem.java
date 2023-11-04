package com.example.bookstore.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    public String id;
    @OneToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    public Book book;
    public double price;
    public int count;

    @ManyToMany(mappedBy = "cartItems")
    private List<Transaction> sales;

    public CartItem(Book book, int count) {
        this.book = book;
        this.count = count;
    }
}
