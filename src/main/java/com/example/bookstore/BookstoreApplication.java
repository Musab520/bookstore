package com.example.bookstore;

import com.example.bookstore.data.Book;
import com.example.bookstore.utilities.HibernateUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class BookstoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        setupFlyway();

        FXMLLoader fxmlLoader = new FXMLLoader(BookstoreApplication.class.getResource("bookstore-root.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setupFlyway() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:3306/bookstore");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("pass123");
        hikariConfig.setSchema("bookstore");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        Flyway flyway = Flyway.configure()
                .schemas("bookstore")
                .locations("src/main/resources/migration/flyway/bookstore")
                .table("flyway_schema_history")
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .validateMigrationNaming(true)
                .target(MigrationVersion.LATEST)
                .dataSource(dataSource)
                .load();
        flyway.repair();
        flyway.migrate();


        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        var entity = session.get(Book.class, 1);

        entity.setAuthor("rayyooon");
        session.save(entity);
        transaction.commit();
    }
}