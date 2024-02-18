package com.example.bookstore.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @OneToMany(mappedBy = "transaction", fetch=FetchType.EAGER)
    private List<CartItem> cartItems;
    @Column(name = "date_purchased")
    private LocalDateTime datePurchased = LocalDateTime.now();
}