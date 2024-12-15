package com.example.myrooms;

import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.Serializable;


public class Book extends StackPane implements Serializable {

    public String bookName;
    public String path;

    public Book(Book b){
        this.bookName = b.bookName;
        this.path = b.path;
    }

    public Book(String bookName,String path) {

        this.bookName = bookName;
        this.path = path;

        ImageView bookImageView = new ImageView(new Image(path));
        bookImageView.setFitWidth(25);
        bookImageView.setFitHeight(110);


        //bookImageView.setPreserveRatio(true);

        this.setPrefSize(bookImageView.getFitWidth()+5, bookImageView.getFitHeight());
        Label bookLabel = new Label(bookName);
        bookLabel.setWrapText(true);
        bookLabel.setPrefWidth(150);
        bookLabel.setTranslateY(-(bookImageView.getFitHeight()/2) +20);
        bookLabel.setStyle(" -fx-background-color: transparent; " +
                "-fx-text-fill: white; -fx-font-size: 10px;-fx-text-overrun: clip;");
        bookLabel.setRotate(90);
        this.getChildren().addAll(bookImageView, bookLabel);



    } public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookName() {
        return bookName;
    }
    public void setImage(String imagePath) {
        this.path = imagePath;
    }
    public int compareTo(Book other){
        return this.getBookName().compareTo(other.getBookName());
    }






}
