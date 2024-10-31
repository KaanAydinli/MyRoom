package com.example.myrooms;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    @FXML
    StackPane stackPane;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView imageView;
    @FXML
    Pane myPane;

    public void goToRoom() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainRoom.fxml"));


        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(70);
        rotate.setDuration(Duration.seconds(2));
        rotate.setNode(myPane);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), anchorPane);
        scaleTransition.setToX(8.0);
        scaleTransition.setToY(8.0);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setOnFinished(event ->{
            root.scaleXProperty().set(8.0);
            root.scaleYProperty().set(8.0);
            stackPane.getChildren().add(root);

            stackPane.getChildren().remove(anchorPane);

            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(1.5), root);
            scaleTransition2.setToX(1.0);
            scaleTransition2.setToY(1.0);
            scaleTransition2.setFromX(8);
            scaleTransition2.setFromY(8);

            scaleTransition2.play();

        });
        rotate.play();
        rotate.setOnFinished(event -> {
            scaleTransition.play();
        });


    }
}
