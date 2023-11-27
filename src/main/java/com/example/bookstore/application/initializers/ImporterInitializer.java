package com.example.bookstore.application.initializers;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.domain.service.BookService;
import com.example.bookstore.utilities.MessageHelper;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImporterInitializer {
    Button templateButton;
    Button sourceButton;
    TextField pathText;
    Button importButton;
    private BookService bookService;
    File file;

    public ImporterInitializer(BookService bookService,Button templateButton, Button sourceButton, TextField pathText, Button importButton){
        this.templateButton = templateButton;
        this.sourceButton = sourceButton;
        this.pathText = pathText;
        this.importButton = importButton;
        this.bookService = bookService;
        init();
    }

    private void init(){
        setupTemplateButton();
        setupSourceButton();
        setupBulkImport();
    }

    private void setupBulkImport(){
        importButton.setOnAction(e->{
            if(file == null)
                MessageHelper.showError("Pick a source file first please","Source File Not Selected");
            try {
                List<Book> books = new ArrayList<>();
                Scanner reader = new Scanner(file, StandardCharsets.UTF_8.name());
                reader.nextLine();
                while(reader.hasNextLine())
                {
                    addLineToBooks(books, reader.nextLine());
                }
                if(!books.isEmpty())
                    bookService.save(books);
            } catch (FileNotFoundException ex) {
                MessageHelper.showError("Failed to locate file", "File not found!");
            }
            MessageHelper.showSuccess("Successfully imported new books.", "Successful Import");
        });
    }

    private void addLineToBooks(List<Book> books, String s) {
        var strings = s.split(",");
        var book = new Book();
        book.setCount(1);
        if(strings.length >= 2){
            if(isNullOrEmpty(strings[0]) || isNullOrEmpty(strings[1]))
                return;
            book.setTitle(strings[0]);
            book.setAuthor(strings[1]);
        }

        if(strings.length >= 3) {
            if (!isNullOrEmpty(strings[2]))
                book.setPublisher(strings[2]);
        }
        if(strings.length >= 4) {
            if (!isNullOrEmpty(strings[3]) && isInteger(strings[3]))
                book.setRow(Integer.parseInt(strings[3]));
        }
        if(strings.length >= 5) {
            if (!isNullOrEmpty(strings[4]))
                book.setShelf(strings[4]);
        }
        if(strings.length >= 6) {
            if (!isNullOrEmpty(strings[5]) && isDouble(strings[5]))
                book.setCost(Double.parseDouble(strings[5]));
        }
        if(strings.length >= 7) {
            if (!isNullOrEmpty(strings[6]) && isDouble(strings[6]))
                book.setPrice(Double.parseDouble(strings[6]));
        }
        if(strings.length >= 8) {
            if (!isNullOrEmpty(strings[7]) && isInteger(strings[7]))
                book.setCount(Integer.parseInt(strings[7]));
        }
        books.add(book);
    }

    private boolean isNullOrEmpty(String s){
        return s.equals(null)|| s.isEmpty();
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        return true;
    }
    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        return true;
    }

    private void setupSourceButton() {
        sourceButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose CSV File");

            // Set extension filter for CSV files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show the dialog to the user
            Stage stage = new Stage();
            file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                // Do something with the chosen CSV file (e.g., store it in a class variable)
                pathText.setText(file.getAbsolutePath());
            }
        });
    }

    public void setupTemplateButton(){
        templateButton.setOnAction(event -> {
            // Create a DirectoryChooser
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose Destination Directory");

            // Show the dialog to the user
            Stage stage = new Stage();
            File destinationDirectory = directoryChooser.showDialog(stage);

            if (destinationDirectory != null) {
                // Form the destination path using the user's chosen directory
                String destinationPath = destinationDirectory.getAbsolutePath() + File.separator + "ImportTemplate.csv";

                // Content to be written to the new file
                String fileContent = "Title (required),Author (required),Publisher,Row(number),Shelf(number),Cost,Price,Count";

                try {
                    // Using FileOutputStream and OutputStreamWriter with UTF-8 encoding
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(destinationPath), "UTF-8");

                    // Write the content to the new file
                    writer.write(fileContent);
                    writer.close();
                    MessageHelper.showSuccess("File created successfully at: " + destinationPath, "Successful Creation");
                } catch (IOException e) {
                    e.printStackTrace();
                    MessageHelper.showError("Error creating file in the destination folder", "Creation Error");
                }
            }
        });


    }

}
