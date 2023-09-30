package com.example.bookstore.application;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.service.BookService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BookstoreController implements Initializable {
    @FXML
    TableView<Book> bookView = new TableView<>();
    BookStoreInitializer initializer = new BookStoreInitializer();

    private final BookService bookService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer.setupBookTableView(bookView, bookService);
    }
}