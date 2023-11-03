module com.example.bookstore {
    requires java.sql;
    requires org.mariadb.jdbc;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.flywaydb.core;
    requires com.zaxxer.hikari;
    requires static lombok;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.validator;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.validation;
    requires com.google.guice;

    opens com.example.bookstore.application.initializers to javafx.fxml, org.flywaydb.core;
    opens com.example.bookstore.application.controller to javafx.fxml, org.flywaydb.core;
    opens com.example.bookstore to javafx.fxml;
    opens migration.flyway.bookstore;
    opens com.example.bookstore.data.models to org.hibernate.orm.core;

    exports com.example.bookstore;
    exports com.example.bookstore.data.models;
    exports com.example.bookstore.utilities;
    exports com.example.bookstore.data.repository;
    exports com.example.bookstore.domain.repository;
    exports com.example.bookstore.domain.service;
    exports com.example.bookstore.application.service;
    exports com.example.bookstore.application.controller;
    exports com.example.bookstore.application.exceptions;
    exports com.example.bookstore.application.initializers;
}