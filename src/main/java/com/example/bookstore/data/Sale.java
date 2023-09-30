package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Sale {
    private int id;
    private int salesManId;
    private LocalDateTime timestamp;
    public Sale(int salesmanId){
        timestamp = LocalDateTime.now();
        this.salesManId = salesmanId;
    }
}
