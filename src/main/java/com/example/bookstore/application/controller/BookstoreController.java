package com.example.bookstore.application.controller;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.application.initializers.BookStoreInitializer;
import com.example.bookstore.application.initializers.CartGridInitializer;
import com.example.bookstore.data.models.Book;
import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.data.models.Transaction;
import com.example.bookstore.domain.service.BookService;
import com.example.bookstore.domain.service.TransactionService;
import com.example.bookstore.dto.Cart;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BookstoreController implements Initializable {
    private final TransactionService transactionService;
    private static Cart cart = new Cart();
    @FXML
    TableView<Book> bookView = new TableView<>();
    @FXML
    Button addBookButton;
    BookStoreInitializer initializer;
    CartGridInitializer cartGridInitializer;
    @FXML
    TextField searchField;
    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    TableView<CartItem> cartGrid;
    @FXML
    Label totalLabel;
    @FXML
    Button checkout;

    private final BookService bookService;
    private final Injector injector;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer = new BookStoreInitializer(cart);
        cartGridInitializer = new CartGridInitializer(cartGrid,cart,totalLabel,injector);
        initializer.setupBookTableView(bookView, bookService, injector, cartGridInitializer);

        ArrayList<String> list = new ArrayList<>();
        list.add("TITLE");
        list.add("AUTHOR");
        choiceBox.setItems(FXCollections.observableList(list));
        choiceBox.setValue("TITLE");

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(choiceBox.getValue().equals("TITLE")) {
                    bookView.getItems().clear();
                    bookView.getItems().addAll(bookService.searchByTitle(newValue));

                } else if(choiceBox.getValue().equals("AUTHOR")) {
                    bookView.getItems().clear();
                    bookView.getItems().addAll(bookService.searchByAuthor(newValue));
                } else if(newValue.isEmpty() || newValue.isBlank()){
                    bookView.getItems().clear();
                    bookView.getItems().addAll(bookService.list());
                } else {
                    bookView.getItems().clear();
                    bookView.getItems().addAll(bookService.list());
                }

            }
        });

        checkout.setOnMouseClicked(e->{
            var transaction = new Transaction();
            transaction.setCartItems(cart.getCartItems());
            transactionService.save(transaction);
            this.initialize(url,resourceBundle);
        });
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