package com.example.bookstore.data.repository;

import com.example.bookstore.data.models.Book;

import java.util.List;

public interface BookRepository {

    void save(Book book);
    void save(List<Book> books);

    Book getById(int id);

    List<Book> list();

    void update(Book book);

    void update(List<Book> books);

    void delete(String id);
    List<Book> searchByTitle(String searchTerm);

    List<Book> searchByAuthor(String searchTerm);
}
