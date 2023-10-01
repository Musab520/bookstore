package com.example.bookstore.application.controller;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.service.BookService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AddBookController {
    private final BookService bookService;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField rowField;
    @FXML
    private TextField shelfField;
    @FXML
    private TextField costField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField countField;
    @FXML
    TableView<Book> bookView = new TableView<>();

    @FXML
    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        int row = Integer.parseInt(rowField.getText());
        String shelf = shelfField.getText();
        double cost = Double.parseDouble(costField.getText());
        double price = Double.parseDouble(priceField.getText());
        int count = Integer.parseInt(countField.getText());

        Book newBook = new Book(title, author, publisher, row, shelf, cost, price, count);

        bookService.save(newBook);
        bookView.setItems(null);
        bookView.setItems(FXCollections.observableArrayList(bookService.list()));
    }
}
