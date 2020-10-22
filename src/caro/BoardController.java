/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class BoardController implements Initializable {
    @FXML
    AnchorPane boardContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        try {
            int x = (int) (event.getX() / 40);
            int y = (int) (event.getY() / 40);
            Image image = new Image(new FileInputStream(System.getProperty("user.dir") + "/src/assets/cross.png"));
            ImageView imageView = new ImageView(image);
            imageView.setX(x * 40);
            imageView.setY(y * 40);
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            System.out.println(x + " " + y);
            boardContainer.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
