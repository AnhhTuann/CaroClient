/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.io.FileInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.layout.Pane;
import javafx.util.Pair;

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
    @FXML
    private Label myName;
    @FXML
    private Label opponentName;
    @FXML
    private Pane myTurnTimerIndicator;
    @FXML
    private Pane opponentTurnTimerIndicator;
    @FXML
    private Pane myPortrait;
    @FXML
    private Pane opponentPortrait;
    private Map<String, Pair<Pane, Pane>> playerInfos = new HashMap<>();
    private Image crossSprite;
    private Image zeroSprite;
    private Timer gameTimer;
    private Timer turnTimer;
    private int gameInterval;
    private int turnInterval;
    private String currentTurn;
    private final int spriteSize = 32;
    private final int gameDuration = 10 * 60;
    private final int turnDuration = 30;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            crossSprite = new Image(new FileInputStream("./src/assets/cross.png"));
            zeroSprite = new Image(new FileInputStream("./src/assets/nought.png"));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        handler = new BoardHandler(this);
        myName.setText(Client.getAccount().getFullname());
        playerInfos.put(Client.getAccount().getId(), new Pair<>(myPortrait, myTurnTimerIndicator));

        Client.registerHandler(handler);
        startGameTimer();
    }

    private String beautifyNumber(int n) {
        return n < 10 ? "0" + Integer.toString(n) : Integer.toString(n);
    }

    private void startGameTimer() {
        gameInterval = gameDuration;
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

    @FXML
    private void handleButtonAction(MouseEvent event) {
        int col = (int) (event.getX() / spriteSize);
        int row = (int) (event.getY() / spriteSize);
        Client.sendData("MOVE:" + col + ";" + row + ";" + Client.getAccount().getId());
    }

    private void startTurnTimer() {
        if (turnTimer != null) {
            turnTimer.cancel();
        }

        turnInterval = turnDuration;
        turnTimer = new Timer();
        turnTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (turnInterval > 0) {
                    Platform.runLater(() -> {
                        Pane turnTimerIndicator = playerInfos.get(currentTurn).getValue();
                        turnTimerIndicator.setPrefWidth(turnTimerIndicator.getPrefWidth() - 5);
                    });

                    turnInterval--;
                } else {
                    turnTimer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void changeTurn(String playerId) {
        currentTurn = playerId;
        playerInfos.forEach((k, v) -> {
            if (k.equals(currentTurn)) {
                v.getKey().getStyleClass().add("in-turn");
                v.getValue().setVisible(true);
                v.getValue().setPrefWidth(150);
            } else {
                v.getKey().getStyleClass().remove("in-turn");
                v.getValue().setVisible(false);
            }
        });

        startTurnTimer();
    }

    public void setOpponentInfo(String id, String name) {
        opponentName.setText(name);
        playerInfos.put(id, new Pair<>(opponentPortrait, opponentTurnTimerIndicator));
    }

    public void drawMove(int col, int row, String fromPlayer) {
        ImageView imageView = new ImageView(fromPlayer.equals(Client.getAccount().getId()) ? crossSprite : zeroSprite);
        imageView.setX(col * spriteSize);
        imageView.setY(row * spriteSize);
        imageView.setFitHeight(spriteSize);
        imageView.setFitWidth(spriteSize);
        boardContainer.getChildren().add(imageView);
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

    public void stopAllTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }

        if (turnTimer != null) {
            turnTimer.cancel();
        }
    }
}
