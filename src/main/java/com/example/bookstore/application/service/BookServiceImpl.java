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
    public void save(List<Book> books) { bookRepository.save(books);}
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
    public void delete(String id) {
        bookRepository.delete(id);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return bookRepository.searchByAuthor(author);
    }
}
