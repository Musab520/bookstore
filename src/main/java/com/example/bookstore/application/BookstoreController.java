package com.example.bookstore.application;

import com.example.bookstore.data.Book;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import com.example.bookstore.application.BookStoreInitializer;
public class BookstoreController implements Initializable {
    @FXML
    TableView<Book> bookView = new TableView<>();
    BookStoreInitializer initializer = new BookStoreInitializer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer.setupBookTableView(bookView);
    }
}