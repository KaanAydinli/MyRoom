package com.example.myrooms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javafx.scene.image.Image;

public class Notebook
{
    @FXML
    private Pane innerPane; // Example of an FXML field
    @FXML
    private Pane outerNotebook;


    public Notebook(Pane Window)
    {
        System.out.println(getClass().getResource("/com/example/myrooms/notebooks/notebookOuter.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myrooms/notebooks/notebookOuter.fxml"));

        try
        {
            outerNotebook = loader.load();
            System.out.println(outerNotebook);
            Window.getChildren().add(outerNotebook);
            loadFXML("notebook_default");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }



    public void notebookOpen()
    {

        outerNotebook.setVisible(true);
        outerNotebook.toFront();
    }

    public void notebookClose()
    {
        outerNotebook.setVisible(false);
    }

    //loading the wanted
    private void loadFXML(String name)
    {
        String location = String.format("notebooks/%s.fxml",name);

        System.out.println(getClass().getResource(location));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
            loader.setController(this);
            innerPane = loader.load();
            innerPane.setStyle("-fx-background-color: transparent;");
            outerNotebook.getChildren().add(innerPane);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML", e);
        }
    }


}
