package com.example.bookstore.domain.repository;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.data.repository.BookRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BookRepositoryImpl implements BookRepository {

    private final SessionFactory sessionFactory;

    public void save(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(book);
            session.getTransaction().commit();
        }
    }

    @Override
    public void save(List<Book> books) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            books.forEach(e->{session.save(e);});
            session.getTransaction().commit();
        }
    }

    public Book getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book.class, id);
        }
    }

    public List<Book> list() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            return query.list();
        }
    }

    public void update(Book book) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(book);
            session.getTransaction().commit();
        }
    }
    public void update(List<Book> books) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Book book : books) {
                session.update(book);
            }
            session.getTransaction().commit();
        }
    }

    public void delete(String id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                session.delete(book);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Book> searchByTitle(String searchTerm) {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> query = session.createNamedQuery("searchBookByTitle", Book.class);
            query.setParameter("searchTerm", searchTerm);
            return query.list();
        }
    }

    @Override
    public List<Book> searchByAuthor(String searchTerm) {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> query = session.createNamedQuery("searchBookByAuthor", Book.class);
            query.setParameter("searchTerm", searchTerm);
            return query.list();
        }
    }
}

