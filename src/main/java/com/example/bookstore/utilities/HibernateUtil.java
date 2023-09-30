package com.example.bookstore.utilities;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.data.models.Transaction;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // You can configure additional properties here
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Transaction.class);
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());

        sessionFactory = configuration.buildSessionFactory(registryBuilder.build());
    }

}
