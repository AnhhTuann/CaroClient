/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import caroclient.Client;
import caroclient.handler.BoardHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
    private AnchorPane boardContainer;
    @FXML
    private Label gameTimerLabel;
    private Image crossSprite;
    private Image zeroSprite;
    private Timer gameTimer;
    private int gameInterval;
    private final int spriteSize = 32;
    private final int gameDuration = 10 * 60;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            crossSprite = new Image(new FileInputStream("./src/assets/cross.png"));
            zeroSprite = new Image(new FileInputStream("./src/assets/nought.png"));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        handler = new BoardHandler(this);
        Client.registerHandler(handler);

        gameInterval = gameDuration;
        startGameTimer();
    }

    private String beautifyNumber(int n) {
        return n < 10 ? "0" + Integer.toString(n) : Integer.toString(n);
    }

    private void startGameTimer() {
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (gameInterval > 0) {
                    int min = gameInterval / 60;
                    int sec = gameInterval % 60;
                    String timeText = beautifyNumber(min) + ":" + beautifyNumber(sec);

                    Platform.runLater(() -> {
                        gameTimerLabel.setText(timeText);
                    });

                    gameInterval--;
                } else {
                    gameTimer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void drawMove(int col, int row, String fromPlayer) {
        ImageView imageView = new ImageView(fromPlayer.equals(Client.getAccount().getId()) ? crossSprite : zeroSprite);
        imageView.setX(col * spriteSize);
        imageView.setY(row * spriteSize);
        imageView.setFitHeight(spriteSize);
        imageView.setFitWidth(spriteSize);
        boardContainer.getChildren().add(imageView);
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        int col = (int) (event.getX() / spriteSize);
        int row = (int) (event.getY() / spriteSize);
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

        if (gameTimer != null) {
            gameTimer.cancel();
        }

        changeScene("/caroclient/Hub.fxml");
    }
}
