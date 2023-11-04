package com.example.bookstore;


import com.example.bookstore.application.service.BookServiceImpl;
import com.example.bookstore.application.service.CartItemRepositoryImpl;
import com.example.bookstore.application.service.TransactionServiceImpl;
import com.example.bookstore.data.repository.BookRepository;
import com.example.bookstore.data.repository.CartItemRepository;
import com.example.bookstore.data.repository.TransactionRepository;
import com.example.bookstore.domain.repository.BookRepositoryImpl;
import com.example.bookstore.domain.repository.TransactionRepositoryImpl;
import com.example.bookstore.domain.service.BookService;
import com.example.bookstore.domain.service.TransactionService;
import com.example.bookstore.utilities.HibernateUtil;
import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;

public class BookstoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HibernateUtil.class).asEagerSingleton();
        bind(SessionFactory.class).toInstance(HibernateUtil.getSessionFactory());
        bind(BookRepository.class).to(BookRepositoryImpl.class);
        bind(BookService.class).to(BookServiceImpl.class);
        bind(TransactionRepository.class).to(TransactionRepositoryImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);
        bind(CartItemRepository.class).to(CartItemRepositoryImpl.class);
    }
}
