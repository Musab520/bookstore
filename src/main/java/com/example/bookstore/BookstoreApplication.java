package com.example.bookstore;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;

import java.io.IOException;

public class BookstoreApplication extends Application {
    private static Injector injector;

    private static boolean FLYWAY_PASS;
    @Override
    public void start(Stage stage) throws IOException {
            if(FLYWAY_PASS) {
                Parent parent = FXMLLoader.load(BookstoreApplication.class.getResource("bookstore-root.fxml"), null, null,
                        injector::getInstance);
                stage.setScene(new Scene(parent));
                stage.setTitle("Bookstore");
                stage.show();
            }

    }

    @Override
    public void init() {
        try {
            setupFlyway();
        } catch (Exception e) {
            FLYWAY_PASS = false;
            Platform.runLater(() -> displayAlert(Alert.AlertType.ERROR, "Error: Database connection issue.",
                    "Please download MariaDB 10.3."));
        }
        if (FLYWAY_PASS) {
            setupGuice();
        }
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
            FLYWAY_PASS = true;
    }
    private static void displayAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit(); // Close the application if OK is clicked
            }
        });
    }
}