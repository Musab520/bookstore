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
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, book.getCount(), 1);
        quantitySpinner.setValueFactory(valueFactory);
        priceLabel.setText(String.format("%.2f", book.getPrice()));
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

        addToCartButton.setOnMouseClicked(
                e -> {
                    var count = quantitySpinner.getValue();
                    var existingCartItem = cart.getCartItem(book);
                    if (existingCartItem == null) {
                        cart.getCartItems().add(new CartItem(book, count));
                    } else {
                        int amount = count + existingCartItem.getCount();
                        int inventoryCount = existingCartItem.getBook().getCount();
                        if (amount > inventoryCount){
                            existingCartItem.setCount(inventoryCount);
                            showInformation("Number of books in this cart will be equal to the maximum amount in inventory.");
                        }
                        else
                            existingCartItem.setCount(amount);
                    }
                    stage.close();
                }
        );
    }

    private void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not Enough Books");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
