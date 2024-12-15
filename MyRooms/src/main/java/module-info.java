module com.example.myrooms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.web;
    requires jdk.compiler;
    requires java.desktop;
    requires com.opencsv;
    requires com.google.gson;
    requires java.sql;


    opens com.example.myrooms to javafx.fxml;
    exports com.example.myrooms;

}