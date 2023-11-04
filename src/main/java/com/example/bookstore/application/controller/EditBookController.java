package com.example.bookstore.application.controller;

import com.example.bookstore.application.exceptions.BookValidationException;
import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.service.BookService;
import com.example.bookstore.utilities.MessageHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;
@RequiredArgsConstructor
public class EditBookController implements Initializable {
    private final BookService bookService;
    private final TableView<Book> bookView;
    private final TableRow<Book> row;
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
    private Button updateButton;
    @FXML
    private Button deleteButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(titleField, Validator.createEmptyValidator("Title is required"));
        validationSupport.registerValidator(authorField, Validator.createEmptyValidator("Author is required"));
        updateButton.setOnAction(e->{
            editBook();
        });
        deleteButton.setOnAction(e->{
            deleteBook();
        });
        titleField.setText(row.getItem().getTitle());
        authorField.setText(row.getItem().getAuthor());
        publisherField.setText(row.getItem().getPublisher());
        rowField.setText(String.valueOf(row.getItem().getRow()));
        shelfField.setText(row.getItem().getShelf());
        costField.setText(String.valueOf(row.getItem().getCost()));
        priceField.setText(String.valueOf(row.getItem().getPrice()));
        countField.setText(String.valueOf(row.getItem().getCount()));
    }

    private void editBook() {
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
                throw new BookValidationException("All required fields must be filled.");
            }

            int row = 0;
            double cost = 0, price = 0;
            int count = 0;

            if(!rowText.isEmpty()) {
                row = Integer.parseInt(rowText);
            }

            if(!costText.isEmpty()) {
                cost = Double.parseDouble(costText);
            }

            if(!priceText.isEmpty()) {
                price = Double.parseDouble(priceText);
            }

            if(!countText.isEmpty()) {
                count = Integer.parseInt(countText);
            }


            Book updatedBook = new Book(this.row.getItem().getId(), title, author, publisher, row, shelf, cost, price, count);
            bookService.update(updatedBook);
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
            MessageHelper.showSuccess(updatedBook.getTitle() + " successfully updated", "Successfully Updated");
            bookView.getItems().clear();
            bookView.getItems().addAll(bookService.list());
        } catch (NumberFormatException e) {
            MessageHelper.showError("Please enter valid numeric values for 'Row', 'Cost', 'Price', and 'Count'.", "Validation Error!");
        } catch (BookValidationException e) {
            MessageHelper.showError(e.getMessage(), "Validation Error!");
        }
    }

    private void deleteBook() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the book " + row.getItem().getTitle());
        ButtonType buttonTypeOk = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeOk) {
                bookService.delete(row.getItem().getId());
                Stage stage = (Stage) titleField.getScene().getWindow();
                stage.close();
                bookView.getItems().clear();
                bookView.getItems().addAll(bookService.list());
            } else if (result == buttonTypeCancel) {

            }
        });
    }
}
