package com.example.bookstore.application.controller;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.dto.Cart;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
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
    private Button removeFromCart;

    @FXML
    private Button cancelButton;

    private final Stage stage;
    private final Cart cart;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var currentCount = getCurrentCount();
        setupSpinner(currentCount);
        priceLabel.setText(String.format("%.2f", book.getPrice() * currentCount));
        setupListeners();
    }

    private void setupSpinner(int currentCount) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, book.getCount(), currentCount);
        quantitySpinner.setValueFactory(valueFactory);
        TextFormatter<Integer> formatter = new TextFormatter<>(
                new IntegerStringConverter(), // Converter to convert text to Integer
                currentCount, // Default value
                c -> {
                    if (c.getControlNewText().matches("\\d*")) { // Allow only digits
                        return c;
                    } else {
                        return null; // Reject non-integer input
                    }
                });
        quantitySpinner.getEditor().setTextFormatter(formatter);
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
            if(count != 0){
                var existingCartItem = cart.getCartItem(book);
                if (existingCartItem == null) {
                    cart.getCartItems().add(new CartItem(book, count));
                } else {
                    existingCartItem.setCount(count);
                }
            }
            stage.close();
        });

        removeFromCart.setOnMouseClicked(e->{
            var item = cart.getCartItem(book);
            if(item != null)
                cart.getCartItems().remove(item);
            stage.close();
        });
    }

    public int getCurrentCount(){
        var existingCartItem = cart.getCartItem(book);
        return existingCartItem == null ? 0 : existingCartItem.getCount();
    }

}
