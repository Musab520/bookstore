package com.example.bookstore.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    public Book book;
    public double price;
    public int count;

    @JoinColumn(name = "transaction_id")
    @ManyToOne(targetEntity = Transaction.class)
    private Transaction transaction;

    public CartItem(Book book, int count) {
        this.book = book;
        this.count = count;
    }
}
