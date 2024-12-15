package com.example.myrooms;

import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class Book extends StackPane {
    public ImageView bookImageView;
    private Label bookLabel;

    public Book(Book b){
        this.bookLabel = new Label(b.getBookName());
        this.bookImageView = new ImageView(b.bookImageView.getImage());
    }

    public Book(String bookName,String path) {

        bookImageView = new ImageView(new Image(path));
        bookImageView.setFitWidth(25);
        bookImageView.setFitHeight(110);


        //bookImageView.setPreserveRatio(true);

        this.setPrefSize(bookImageView.getFitWidth()+5, bookImageView.getFitHeight());
        bookLabel = new Label(bookName);
        bookLabel.setWrapText(true);
        bookLabel.setPrefWidth(150);
        bookLabel.setTranslateY(-(bookImageView.getFitHeight()/2) +20);
        bookLabel.setStyle(" -fx-background-color: transparent; " +
                "-fx-text-fill: white; -fx-font-size: 10px;-fx-text-overrun: clip;");
        bookLabel.setRotate(90);
        this.getChildren().addAll(bookImageView, bookLabel);



    } public void setBookName(String bookName) {
        bookLabel.setText(bookName);
    }
    public String getBookName() {
        return bookLabel.getText();
    }
    public void setImage(String imagePath) {
        bookImageView.setImage(new Image(imagePath));
    }
    public int compareTo(Book other){
        return this.getBookName().compareTo(other.getBookName());
    }






}
