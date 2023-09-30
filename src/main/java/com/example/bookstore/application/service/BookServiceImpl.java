package com.example.bookstore.application.service;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.data.repository.BookRepository;
import com.example.bookstore.domain.service.BookService;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;


    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getById(int id) {
        return bookRepository.getById(id);
    }

    @Override
    public List<Book> list() {
        return bookRepository.list();
    }

    @Override
    public void update(Book book) {
        bookRepository.update(book);
    }

    @Override
    public void delete(int id) {
        bookRepository.delete(id);
    }
}
