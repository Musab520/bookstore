package com.example.bookstore;

import com.example.bookstore.data.repository.BookRepository;
import com.example.bookstore.utilities.HibernateUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

import java.io.IOException;

public class BookstoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        setupGuice();
        setupFlyway();

        FXMLLoader fxmlLoader = new FXMLLoader(BookstoreApplication.class.getResource("bookstore-root.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setupGuice() {
        Injector injector = Guice.createInjector(new BookstoreModule());
        HibernateUtil hibernateUtil = injector.getInstance(HibernateUtil.class);
        BookRepository bookRepository = injector.getInstance(BookRepository.class);
    }

    public static void setupFlyway() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:33306/bookstore");
        hikariConfig.setUsername("admin");
        hikariConfig.setPassword("pass123");
        hikariConfig.setSchema("bookstore");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        Flyway flyway = Flyway.configure()
                .defaultSchema("bookstore")
                .schemas("bookstore")
                .locations("classpath:/migration/flyway/bookstore/")
                .table("flyway_schema_history")
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .validateMigrationNaming(true)
                .target(MigrationVersion.LATEST)
                .dataSource(dataSource)
                .load();
        flyway.repair();
        flyway.migrate();
    }
}