package com.example.bookstore.application.component;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.application.controller.AddBookController;
import com.example.bookstore.application.controller.AddToCartController;
import com.example.bookstore.data.models.Book;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomIconButtonTableCell extends TableCell<Book, Void> {
    private final Button button2;
    private Injector injector;


    public CustomIconButtonTableCell(String icon) {
        button2 = createIconButton(icon);

        setGraphic(new HBox(button2));
    }

    public CustomIconButtonTableCell(String icon, Injector injector) {
        button2 = createIconButton(icon);
        this.injector = injector;
        setGraphic(new HBox(button2));
    }

    private Button createIconButton(String iconName) {
        Image icon = new Image(getClass().getResourceAsStream(iconName));
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(15);
        iconView.setFitHeight(15);
        Button button = new Button();
        button.setGraphic(iconView);

        // Add event handlers or custom logic for your buttons here.
        // For example, you can set onAction handlers to perform actions when buttons are clicked.

        return button;
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(new HBox(button2));
            button2.setOnAction(e ->{
                try {
                    Book book = this.getTableRow().getItem();
                    FXMLLoader loader = new FXMLLoader(BookstoreApplication.class.getResource("add-to-cart-dialog.fxml"), null, null,
                            injector::getInstance);
                    AddToCartController addToCartController = new AddToCartController(book);
                    loader.setController(addToCartController);
                    Stage stage = new Stage();

                        stage.setScene(new Scene(loader.load()));

                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
}

