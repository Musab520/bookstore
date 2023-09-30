module com.example.bookstore {
    requires java.sql;
    requires org.mariadb.jdbc;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.flywaydb.core;
    requires com.zaxxer.hikari;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.validation;
    exports com.example.bookstore.application;
    opens com.example.bookstore.application to javafx.fxml, org.flywaydb.core;
    opens com.example.bookstore to javafx.fxml;
    exports com.example.bookstore;
}