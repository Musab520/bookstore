package com.example.bookstore.application;

import com.example.bookstore.application.component.CustomIconButtonTableCell;
import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.service.BookService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class BookStoreInitializer {
    public BookStoreInitializer(){}
    public void setupBookTableView(TableView<Book> bookView, BookService bookService) {
        List<TableColumn<Book, ?>> columns = new ArrayList<>();
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> p) {
                return Bindings.createStringBinding(() -> p.getValue().getTitle());
            }
        });
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> p) {
                return Bindings.createStringBinding(() -> p.getValue().getAuthor());
            }
        });
        TableColumn<Book, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> p) {
                return Bindings.createStringBinding(() -> p.getValue().getPublisher());
            }
        });
        TableColumn<Book, Integer> rowColumn = new TableColumn<>("Row");
        rowColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Book, Integer> p) {
                return Bindings.createIntegerBinding(() -> p.getValue().getRow()).asObject();
            }
        });
        TableColumn<Book, String> shelfColumn = new TableColumn<>("Shelf");
        shelfColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> p) {
                return Bindings.createStringBinding(() -> p.getValue().getShelf());
            }
        });
        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Book, Double> p) {
                return Bindings.createDoubleBinding(() -> p.getValue().getPrice()).asObject();
            }
        });
        TableColumn<Book, Void> customIconButtonColumn = new TableColumn<>("Actions");
        customIconButtonColumn.setCellFactory(column -> new CustomIconButtonTableCell());


        columns.add(titleColumn);
        columns.add(authorColumn);
        columns.add(publisherColumn);
        columns.add(rowColumn);
        columns.add(shelfColumn);
        columns.add(priceColumn);
        columns.add(customIconButtonColumn);


        bookView.getColumns().addAll(columns);

        ObservableList<Book> books = FXCollections.observableArrayList();
        books.addAll(bookService.list());
        bookView.setItems(books);
    }
}
