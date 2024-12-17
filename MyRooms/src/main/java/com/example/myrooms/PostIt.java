package com.example.myrooms;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Objects;

public class PostIt extends StackPane implements Serializable {
    private int colorOfPostIt;
    private String textOfPostIt;
    private String titleOfPostIt;

    public PostIt(String title, int colorOfPostIt)
    {

        this.colorOfPostIt = colorOfPostIt;
        this.titleOfPostIt = title;

        if(colorOfPostIt == 0)
        {
            this.setStyle(" -fx-background-color: BEIGE");
        }
        if(colorOfPostIt==1)
        {
            this.setStyle(" -fx-background-color: #d3d30f");
        }
        if(colorOfPostIt == 2)
        {
            this.setStyle(" -fx-background-color: #7e5c7e");
        }
        if(colorOfPostIt==3)
        {
            this.setStyle(" -fx-background-color: #89ba89");
        }
        if(colorOfPostIt==4)
        {
            this.setStyle(" -fx-background-color: #0101e4");
        }

        this.setPrefHeight(32);
        this.setPrefWidth(38);

        TextField textOne = new TextField(title);
        this.setAlignment(textOne, Pos.CENTER);
        textOne.setEditable(false);

        textOne.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-border-width: 1;");
        this.getChildren().add(textOne);

    }

    public PostIt minimizePostIt()
    {

        return new PostIt(this.getTitle(), this.getColor());
    }

    public PostIt(String title, String text, int colorOfPostIt)
    {

        this.colorOfPostIt = colorOfPostIt;
        this.titleOfPostIt = title;
        this.textOfPostIt = text;

        if(colorOfPostIt == 0)
        {
            this.setStyle(" -fx-background-color: BEIGE");
        }
        if(colorOfPostIt==1)
        {
            this.setStyle(" -fx-background-color: #d3d30f");
        }
        if(colorOfPostIt == 2)
        {
            this.setStyle(" -fx-background-color: #7e5c7e");
        }
        if(colorOfPostIt==3)
        {
            this.setStyle(" -fx-background-color: #89ba89");
        }
        if(colorOfPostIt==4)
        {
            this.setStyle(" -fx-background-color: #0101e4");
        }
    }

    public int getColor()
    {
        return this.colorOfPostIt;
    }

    public void setColor(int color)
    {
        this.colorOfPostIt = color;

    }

    public String getTitle()
    {
        return this.titleOfPostIt;
    }

    //creates new PostIt
    public void setTitle(String newTitle)
    {
        this.titleOfPostIt = newTitle;
    }

    public String getText()
    {
        return this.textOfPostIt;
    }

    public void setText(String newText)
    {
        this.textOfPostIt = newText;
    }

}



