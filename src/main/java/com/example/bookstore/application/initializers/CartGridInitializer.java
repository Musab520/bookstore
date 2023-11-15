package com.example.bookstore.application.initializers;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.application.controller.AddToCartController;
import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.dto.Cart;
import com.google.inject.Injector;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class CartGridInitializer {

    private TableView<CartItem> grid;
    private Cart cart;
    private Label totalText;
    private Injector injector;

    private boolean isHistory;

    public CartGridInitializer(TableView<CartItem> grid, Cart cart, Label totalText, Injector injector, boolean isHistory) {
        this.grid = grid;
        this.cart = cart;
        this.totalText = totalText;
        this.injector = injector;
        this.isHistory = isHistory;
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
        if(!isHistory){
            grid.setRowFactory(e -> {
                TableRow<CartItem> row = new TableRow<>();
                row.setOnMouseClicked(f -> {
                    if (!row.isEmpty() && f.getButton() == MouseButton.PRIMARY && f.getClickCount() == 2) {
                        FXMLLoader loader = new FXMLLoader(BookstoreApplication.class.getResource("add-to-cart-dialog.fxml"), null, null,
                                injector::getInstance);
                        var stage = new Stage();
                        AddToCartController addToCartController = new AddToCartController(row.getItem().getBook(),stage, cart);
                        loader.setController(addToCartController);

                        try {
                            stage.setScene(new Scene(loader.load()));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                        this.init();
                    }
                });
                return row;
            });
        }
    }
}
