package com.example.bookstore.application.controller;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.application.initializers.BookStoreInitializer;
import com.example.bookstore.application.initializers.CartGridInitializer;
import com.example.bookstore.application.initializers.ImporterInitializer;
import com.example.bookstore.application.initializers.TransactionsTabInitializer;
import com.example.bookstore.data.models.Book;
import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.data.models.Transaction;
import com.example.bookstore.domain.service.BookService;
import com.example.bookstore.domain.service.CartItemService;
import com.example.bookstore.domain.service.TransactionService;
import com.example.bookstore.dto.Cart;
import com.example.bookstore.utilities.MessageHelper;
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
    @FXML
    TableView<Book> bookView = new TableView<>();
    @FXML
    Button addBookButton;
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
    @FXML
    DatePicker transactionDatePicker;
    @FXML
    Button transactionSearchButton;
    @FXML
    TableView<Transaction> transactionView;
    @FXML
    TableView<CartItem> transactionCartView;
    @FXML
    Label transactionTotalLabel;
    @FXML
    Button templateButton;
    @FXML
    Button sourceButton;
    @FXML
    TextField pathText;
    @FXML
    Button importButton;
    @FXML
    Button refreshButton;

    private final TransactionService transactionService;
    private final CartItemService cartItemService;
    private static Cart cart = new Cart();
    private BookStoreInitializer initializer;
    private CartGridInitializer cartGridInitializer;
    private TransactionsTabInitializer transactionsTabInitializer;
    private final BookService bookService;
    private final Injector injector;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer = new BookStoreInitializer(cart);
        cartGridInitializer = new CartGridInitializer(cartGrid,cart,totalLabel,injector, false);
        initializer.setupBookTableView(bookView, bookService, injector, cartGridInitializer);
        transactionsTabInitializer = new TransactionsTabInitializer();
        transactionsTabInitializer.init(transactionView,transactionService, transactionCartView,transactionTotalLabel,
                transactionSearchButton, transactionDatePicker, injector);
        new ImporterInitializer(bookService,templateButton,sourceButton,pathText,importButton);

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
            var flag = MessageHelper.showConfirmation("Are you sure you are done with this transaction?","Confirm Transaction");
            if (flag){
                var transaction = new Transaction();
                transaction.setCartItems(cart.getCartItems());
                transactionService.save(transaction);
                cart = new Cart();
                this.initialize(url,resourceBundle);
            }
        });

        refreshButton.setOnAction(e->{
            initializer.setupBookTableView(bookView, bookService, injector, cartGridInitializer);
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