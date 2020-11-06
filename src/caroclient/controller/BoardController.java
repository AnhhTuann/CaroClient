/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import caroclient.Client;
import caroclient.handler.BoardHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class BoardController extends ControllerBase {

    @FXML
    AnchorPane boardContainer;
    private Image crossSprite;
    private Image zeroSprite;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            crossSprite = new Image(new FileInputStream("./src/assets/cross.png"));
            zeroSprite = new Image(new FileInputStream("./src/assets/zero.png"));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        handler = new BoardHandler(this);
        Client.registerHandler(handler);
    }

    public void drawMove(int col, int row, String fromPlayer) {
        ImageView imageView = new ImageView(fromPlayer.equals(Client.getAccount().getId()) ? crossSprite : zeroSprite);
        imageView.setX(col * 40);
        imageView.setY(row * 40);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        boardContainer.getChildren().add(imageView);
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        int col = (int) (event.getX() / 40);
        int row = (int) (event.getY() / 40);
        Client.sendData("MOVE:" + col + ";" + row + ";" + Client.getAccount().getId());
    }

    public void showGameOverDialog(String status) {
        Alert alert = new Alert(AlertType.INFORMATION);
        String text = status.equals("DRAW") ? "DRAW! WELL PLAYED!"
                : status.equals(Client.getAccount().getId()) ? "CONGRATULATION! YOU WIN!" : "SORRY! YOU LOSE!";

        alert.setTitle("GameOver");
        alert.setContentText(text);
        alert.initOwner(stage);
        alert.showAndWait();
        changeScene("/caroclient/Hub.fxml");
    }
}
