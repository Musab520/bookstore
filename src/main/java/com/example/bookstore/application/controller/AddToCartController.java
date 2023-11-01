package com.example.bookstore.application.controller;

import com.example.bookstore.data.models.Book;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;
@RequiredArgsConstructor()
public class AddToCartController implements Initializable {
    private final Book book;
    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button cancelButton;

    private final Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, book.getCount(), 1);
        quantitySpinner.setValueFactory(valueFactory);
        priceLabel.setText(String.format("%.2f", book.getPrice()));
        setupListeners();
    }

    private void setupListeners(){
        quantitySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                double totalPrice = book.getPrice() * newValue;
                priceLabel.setText(String.format("%.2f", totalPrice));
            }
        });

        cancelButton.setOnMouseClicked(e-> stage.close());
    }

}
