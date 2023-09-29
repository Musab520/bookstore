package com.example.bookstore.data;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Sale {
    private int id;
    private int salesManId;
    private LocalDateTime timestamp;
    public Sale(int salesmanId){
        timestamp = LocalDateTime.now();
        this.salesManId = salesmanId;
    }
    public Sale(){}
}
