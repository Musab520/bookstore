package com.example.bookstore.application.controller;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.application.BookStoreInitializer;
import com.example.bookstore.application.service.BookServiceImpl;
import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.repository.BookRepositoryImpl;
import com.example.bookstore.domain.service.BookService;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BookstoreController implements Initializable {
    @FXML
    TableView<Book> bookView = new TableView<>();
    @FXML
    Button addBookButton;
    BookStoreInitializer initializer = new BookStoreInitializer();

    private final BookService bookService;
    private final Injector injector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer.setupBookTableView(bookView, bookService);
    }

    public void openAddBookDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(BookstoreApplication.class.getResource("add-book-dialog.fxml"), null, null,
                injector::getInstance);
        AddBookController addBookController = new AddBookController(bookService, bookView);
        loader.setController(addBookController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}