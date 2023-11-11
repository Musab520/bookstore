package com.example.bookstore;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

import java.io.IOException;

public class BookstoreApplication extends Application {
    private static Injector injector;
    @Override
    public void start(Stage stage) throws IOException {
            Parent parent = FXMLLoader.load(BookstoreApplication.class.getResource("bookstore-root.fxml"), null, null,
                    injector::getInstance);
            stage.setScene(new Scene(parent));
            stage.setTitle("Bookstore");
            stage.show();

    }

    @Override
    public void init() throws IOException {
        setupFlyway();
        setupGuice();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setupGuice() {
        injector = Guice.createInjector(new BookstoreModule());
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