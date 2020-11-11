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

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class BoardController extends ControllerBase {
    private class PlayerInfo {
        private Pane portrait;
        private Pane timerIndicator;
        private Image moveIcon;
        private String name;

        public PlayerInfo(Pane portrait, Pane timerIndicator, Image moveIcon, String name) {
            this.portrait = portrait;
            this.timerIndicator = timerIndicator;
            this.moveIcon = moveIcon;
            this.name = name;
        }
    }

    @FXML
    private AnchorPane boardContainer;
    @FXML
    private Label gameTimerLabel;
    @FXML
    private Label p1Name;
    @FXML
    private Label p2Name;
    @FXML
    private Pane p1TurnTimerIndicator;
    @FXML
    private Pane p2TurnTimerIndicator;
    @FXML
    private Pane p1Portrait;
    @FXML
    private Pane p2Portrait;
    private Map<String, PlayerInfo> playerInfos = new HashMap<>();
    private Image crossSprite;
    private Image noughtSprite;
    private Timer gameTimer;
    private Timer turnTimer;
    private int gameInterval;
    private int turnInterval;
    private String currentTurn;
    private boolean isSpectating = false;
    private final int spriteSize = 32;
    private final int gameDuration = 10 * 60;
    private final int turnDuration = 30;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            crossSprite = new Image(new FileInputStream("./src/assets/cross.png"));
            noughtSprite = new Image(new FileInputStream("./src/assets/nought.png"));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        handler = new BoardHandler(this);

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
                        Pane turnTimerIndicator = playerInfos.get(currentTurn).timerIndicator;
                        turnTimerIndicator.setPrefWidth(turnTimerIndicator.getPrefWidth() - 5);
                    });

                    turnInterval--;
                } else {
                    turnTimer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void startSpectating(String p1Name, String p1Id, String p2Name, String p2Id, String[] moves) {
        isSpectating = true;
        this.p1Name.setText(p1Name);
        playerInfos.put(p1Id, new PlayerInfo(p1Portrait, p1TurnTimerIndicator, crossSprite, p1Name));
        this.p2Name.setText(p2Name);
        playerInfos.put(p2Id, new PlayerInfo(p2Portrait, p2TurnTimerIndicator, noughtSprite, p2Name));

        for (String move : moves) {
            String[] data = move.split("\\.");
            drawMove(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]);
        }
    }

    public void startGame(String opponentId, String opponentName) {
        isSpectating = false;
        String myName = Client.getAccount().getFullname();
        p1Name.setText(Client.getAccount().getFullname());
        playerInfos.put(Client.getAccount().getId(),
                new PlayerInfo(p1Portrait, p1TurnTimerIndicator, crossSprite, myName));
        this.p2Name.setText(opponentName);
        playerInfos.put(opponentId, new PlayerInfo(p2Portrait, p2TurnTimerIndicator, noughtSprite, opponentName));
    }

    public void changeTurn(String playerId) {
        currentTurn = playerId;
        playerInfos.forEach((k, v) -> {
            if (k.equals(currentTurn)) {
                v.portrait.getStyleClass().add("in-turn");
                v.timerIndicator.setVisible(true);
                v.timerIndicator.setPrefWidth(150);
            } else {
                v.portrait.getStyleClass().remove("in-turn");
                v.timerIndicator.setVisible(false);
            }

            if (isSpectating) {
                v.timerIndicator.setVisible(false);
            }
        });

        if (!isSpectating) {
            startTurnTimer();
        }
    }

    public void drawMove(int col, int row, String fromPlayer) {
        ImageView imageView = new ImageView(playerInfos.get(fromPlayer).moveIcon);
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
        String spectatingText = "GAME OVER! "
                + (status.equals("DRAW") ? "DRAW!" : playerInfos.get(status).name + " WIN!");
        alert.setTitle("GameOver");
        alert.setContentText(isSpectating ? spectatingText : text);
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
