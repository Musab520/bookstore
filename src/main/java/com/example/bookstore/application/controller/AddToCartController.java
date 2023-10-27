package com.example.bookstore.application.controller;

import com.example.bookstore.data.models.Book;
import javafx.fxml.Initializable;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ResourceBundle;
@RequiredArgsConstructor()
public class AddToCartController implements Initializable {
    private final Book book;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
