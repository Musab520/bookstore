package com.example.bookstore.application.initializers;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.application.component.CustomIconButtonTableCell;
import com.example.bookstore.application.controller.EditBookController;
import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.service.BookService;
import com.example.bookstore.dto.Cart;
import com.google.inject.Injector;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookStoreInitializer {

    private final Cart cart;
    public BookStoreInitializer(Cart cart){
        this.cart = cart;
    }
    public void setupBookTableView(TableView<Book> bookView, BookService bookService, Injector injector, CartGridInitializer cartGridInitializer) {
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
        TableColumn<Book, Integer> countColumn = new TableColumn<>("Count");
        countColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Book, Integer> p) {
                return Bindings.createIntegerBinding(() -> p.getValue().getCount()).asObject();
            }
        });
        TableColumn<Book, Void> customIconButtonColumn = new TableColumn<>("Actions");
        customIconButtonColumn.setCellFactory(column -> new CustomIconButtonTableCell("/assets/icons/cart-plus.png", injector, cart,cartGridInitializer));

        bookView.setRowFactory(e -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(f -> {
                if (!row.isEmpty() && f.getButton() == MouseButton.PRIMARY && f.getClickCount() == 2) {
                    FXMLLoader loader = new FXMLLoader(BookstoreApplication.class.getResource("edit-book-dialog.fxml"), null, null,
                            injector::getInstance);
                    EditBookController editBookController = new EditBookController(bookService, bookView, row);
                    loader.setController(editBookController);
                    Stage stage = new Stage();
                    try {
                        stage.setScene(new Scene(loader.load()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                }
            });
            return row;
        });


        columns.add(titleColumn);
        columns.add(authorColumn);
        columns.add(publisherColumn);
        columns.add(rowColumn);
        columns.add(shelfColumn);
        columns.add(priceColumn);
        columns.add(countColumn);
        columns.add(customIconButtonColumn);
        columns.forEach(e ->{
            e.setPrefWidth(111);
        });


        bookView.getColumns().clear();
        bookView.getColumns().addAll(columns);

        ObservableList<Book> books = FXCollections.observableArrayList();
        books.addAll(bookService.list());
        bookView.setItems(books);
    }
}
