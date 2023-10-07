package com.example.bookstore.domain.service;

import com.example.bookstore.data.models.Book;

import java.util.List;

public interface BookService {
    void save(Book book);

    Book getById(int id);

    List<Book> list();

    void update(Book book);

    void delete(String id);

    List<Book> searchByTitle(String title);

    List<Book> searchByAuthor(String author);
}
