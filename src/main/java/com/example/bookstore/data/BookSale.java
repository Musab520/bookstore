package com.example.bookstore.data;

import lombok.Data;
@Data
public class BookSale {
    private int id;
    private int saleId;
    private int bookId;
    public BookSale(int saleId, int bookId){
        this.saleId = saleId;
        this.bookId = bookId;
    }
    public BookSale(){}
}
