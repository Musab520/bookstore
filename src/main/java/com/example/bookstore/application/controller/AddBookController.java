package com.example.bookstore.application.controller;

import com.example.bookstore.application.BookStoreInitializer;
import com.example.bookstore.application.exceptions.BookValidationException;
import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.service.BookService;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.WindowEvent;
import lombok.RequiredArgsConstructor;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;

@RequiredArgsConstructor()
public class AddBookController implements Initializable {
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
    private final TableView<Book> bookView;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label rowLabel;
    @FXML
    private Label shelfLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label countLabel;
    @FXML
    private Button addButton;


    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        String rowText = rowField.getText();
        String shelf = shelfField.getText();
        String costText = costField.getText();
        String priceText = priceField.getText();
        String countText = countField.getText();

        try {

        if (title.isEmpty() || author.isEmpty()) {
            showError("All required fields must be filled.");
            throw new BookValidationException("All required fields must be filled.");
        }

        int row = 0;
        double cost = 0, price = 0;
        int count = 0;

        if(!rowText.isEmpty()) {
            row = Integer.parseInt(rowText);
        }

        if(!costText.isEmpty()) {
            cost = Integer.parseInt(costText);
        }

        if(!priceText.isEmpty()) {
            price = Integer.parseInt(priceText);
        }

        if(!countText.isEmpty()) {
            count = Integer.parseInt(countText);
        }


        Book newBook = new Book(title, author, publisher, row, shelf, cost, price, count);
        bookService.save(newBook);
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
        showSuccess(newBook.getTitle() + " successfully added");
        bookView.getItems().clear();
        bookView.getItems().addAll(bookService.list());
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values for 'Row', 'Cost', 'Price', and 'Count'.");
        } catch (BookValidationException e) {
            showError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(titleField, Validator.createEmptyValidator("Title is required"));
        validationSupport.registerValidator(authorField, Validator.createEmptyValidator("Author is required"));
        addButton.setOnAction(e->{
            addBook();
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book successfully added");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
