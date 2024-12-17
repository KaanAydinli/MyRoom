module com.example.myrooms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.web;
    requires jdk.compiler;
    requires java.desktop;
    requires com.google.gson;
    requires java.sql;

    exports com.example.myrooms;

    opens com.example.myrooms to
            javafx.fxml;
}