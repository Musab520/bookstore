package com.example.bookstore.application.component;

import com.example.bookstore.data.models.Book;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CustomIconButtonTableCell extends TableCell<Book, Void> {
    private final Button button2;

    public CustomIconButtonTableCell() {
        button2 = createIconButton("/assets/icons/cart-plus.png");

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
        }
    }
}

