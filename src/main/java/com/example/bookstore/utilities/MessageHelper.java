package com.example.bookstore.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageHelper {

    public static void showError(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showSuccess(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static boolean showConfirmation(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Show the confirmation dialog and wait for user response
        alert.showAndWait();

        // Check if the user clicked OK (Confirmation) button
        return alert.getResult() == ButtonType.OK;
    }
}
