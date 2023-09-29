module com.example.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    exports com.example.bookstore.application;
    opens com.example.bookstore.application to javafx.fxml;
    opens com.example.bookstore to javafx.fxml;
    exports com.example.bookstore;
}