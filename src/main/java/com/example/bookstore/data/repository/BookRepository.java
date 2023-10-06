package com.example.bookstore.data.repository;

import com.example.bookstore.data.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public interface BookRepository {

    void save(Book book);

    Book getById(int id);

    List<Book> list();

    void update(Book book);

    void delete(String id);
}
