package com.example.bookstore.application.initializers;

import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.data.models.Transaction;
import com.example.bookstore.domain.service.TransactionService;
import com.example.bookstore.dto.Cart;
import com.google.inject.Injector;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionsTabInitializer {

    public void init(TableView<Transaction> grid, TransactionService transactionService, TableView<CartItem> cartGrid,
                     Label totalLabel, Button searchButton, DatePicker datePicker, Injector injector){
        List<TableColumn<Transaction, ?>> columns = new ArrayList<>();

        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new Callback<>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> p) {
                return Bindings.createStringBinding(() -> {
                    LocalDateTime dateTime = p.getValue().getDatePurchased();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    return dateTime.format(formatter);
                });
            }
        });

        TableColumn<Transaction, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new Callback<>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Transaction, Double> p) {
                return Bindings.createDoubleBinding(() -> {
                    var items = p.getValue().getCartItems();
                    double sum = 0.0;
                    for (CartItem cartItem : items) {
                        sum += cartItem.getCount() * cartItem.getPrice();
                    }
                    return sum;
                }).asObject();
            }
        });

        grid.setRowFactory(e -> {
            TableRow<Transaction> row = new TableRow<>();
            row.setOnMouseClicked(f -> {
                if (!row.isEmpty() && f.getButton() == MouseButton.PRIMARY && f.getClickCount() == 2) {
                    Cart cart = new Cart();
                    cart.setCartItems(row.getItem().getCartItems());
                    new CartGridInitializer(cartGrid,cart,totalLabel,injector, false);
                }
            });
            return row;
        });

        searchButton.setOnMouseClicked(f->{
            if(datePicker.getValue() != null){
                ObservableList<Transaction> transactions = FXCollections.observableArrayList();
                var list = transactionService.listFromDate(datePicker.getValue());
                transactions.addAll(list);
                grid.setItems(transactions);
            }else
            {
                this.init(grid,transactionService,cartGrid,totalLabel,searchButton,datePicker,injector);
            }
        });
        columns.add(dateColumn);
        columns.add(totalColumn);
        grid.getColumns().clear();
        grid.getColumns().addAll(columns);
        columns.forEach(e ->{
            e.setPrefWidth(111);
        });
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        var list = transactionService.list();
        transactions.addAll(list);
        grid.setItems(transactions);
    }
}
