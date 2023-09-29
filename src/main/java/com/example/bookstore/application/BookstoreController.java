package com.example.bookstore.application;

import com.example.bookstore.data.Book;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
public class BookstoreController implements Initializable {
    @FXML
    TableView<Book> bookView = new TableView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<TableColumn<Book, String>> columns = Arrays.stream(Book.class.getDeclaredFields())
                .map(field -> new TableColumn<Book, String>(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)))
                .toList();


        bookView.getColumns().addAll(columns);

        ObservableList<Book> books = FXCollections.observableArrayList();
        books.add(new Book("musab"));
        bookView.setItems(books);
    }
}