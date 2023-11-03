package com.example.bookstore.application.initializers;

import com.example.bookstore.dto.Cart;
import com.example.bookstore.dto.CartItem;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class CartGridInitializer {

    private TableView<CartItem> grid;
    private Cart cart;
    private Label totalText;

    public CartGridInitializer(TableView<CartItem> grid, Cart cart, Label totalText) {
        this.grid = grid;
        this.cart = cart;
        this.totalText = totalText;
        init();
    }


    public void init(){
        List<TableColumn<CartItem, ?>> columns = new ArrayList();
        TableColumn<CartItem, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CartItem, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CartItem, String> p) {
                return Bindings.createStringBinding(() -> p.getValue().getBook().getTitle());
            }
        });

        TableColumn<CartItem, Integer> countColumn = new TableColumn<>("Count");
        countColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CartItem, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<CartItem, Integer> p) {
                return Bindings.createIntegerBinding(() -> p.getValue().getCount()).asObject();
            }
        });

        TableColumn<CartItem, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CartItem, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<CartItem, Double> p) {
                return Bindings.createDoubleBinding(() -> p.getValue().getBook().getPrice() * p.getValue().getCount()).asObject();
            }
        });

        columns.add(titleColumn);
        columns.add(countColumn);
        columns.add(totalColumn);
        columns.forEach(e ->{
            e.setPrefWidth(111);
        });
        grid.getColumns().setAll(columns);
        ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
        cartItems.addAll(cart.getCartItems());
        grid.setItems(cartItems);

        var sum = cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getBook().getPrice() * cartItem.getCount())
                .sum();
        totalText.setText(String.format("%.2f$", sum));
    }
}
