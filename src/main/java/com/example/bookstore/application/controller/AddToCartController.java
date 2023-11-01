package com.example.bookstore.application.controller;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.dto.Cart;
import com.example.bookstore.dto.CartItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private final Cart cart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var currentCount = getCurrentCount();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, book.getCount(), currentCount);
        quantitySpinner.setValueFactory(valueFactory);
        priceLabel.setText(String.format("%.2f", book.getPrice() * currentCount));
        setupListeners();
    }

    private void setupListeners() {
        quantitySpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                double totalPrice = book.getPrice() * newValue;
                priceLabel.setText(String.format("%.2f", totalPrice));
            }
        });

        cancelButton.setOnMouseClicked(e -> stage.close());

        addToCartButton.setOnMouseClicked(e -> {
            var count = quantitySpinner.getValue();
            var existingCartItem = cart.getCartItem(book);
            if (existingCartItem == null) {
                cart.getCartItems().add(new CartItem(book, count));
            } else {
                existingCartItem.setCount(count);
            }
            stage.close();
        });
    }

    public int getCurrentCount(){
        var existingCartItem = cart.getCartItem(book);
        return existingCartItem == null ? 1 : existingCartItem.getCount();
    }

}
