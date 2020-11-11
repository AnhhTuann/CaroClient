/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import caroclient.Client;
import caroclient.handler.HubHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class HubController extends ControllerBase {
    @FXML
    private Label playerNameLabel;
    @FXML
    private VBox roomMenu;
    @FXML
    private Button refreshButton;
    @FXML
    private Button exitButton;
    private ButtonType acceptButton = new ButtonType("Accept", ButtonData.OK_DONE);
    private ButtonType declineButton = new ButtonType("Decline", ButtonData.CANCEL_CLOSE);
    private Alert matchFoundAlert = new Alert(AlertType.CONFIRMATION,
            "We have found for you an opponent! Please accept!", acceptButton, declineButton);
    private Alert waitForAnotherAlert = new Alert(AlertType.NONE, "Waiting for another to response...");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new HubHandler(this);
        Client.registerHandler(handler);

        playerNameLabel.setText(Client.getAccount().getFullname());
        Client.sendData("READY:" + Client.getAccount().getId());

        matchFoundAlert.setTitle("Found a match!");
        waitForAnotherAlert.setTitle("Found a match!");
        waitForAnotherAlert.setResult(ButtonType.OK);
    }

    @FXML
    public void refreshGameList() {
        Client.sendData("REFRESH:Refresh game list");
    }

    @FXML
    void exitGame() {
        showConfirmExitDialog();
    }

    public void listGameRoom(String[] gameRoom) {
        roomMenu.getChildren().clear();

        if (gameRoom == null || gameRoom.length == 0) {
            Label emptyLabel = new Label();
            emptyLabel.setText("Such an empty here!");
            roomMenu.getChildren().add(emptyLabel);
        } else {
            try {
                for (int i = 0; i < gameRoom.length; i++) {
                    String[] info = gameRoom[i].split(",");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/RoomMenuItem.fxml"));
                    Node item = loader.load();
                    RoomMenuItemController controller = loader.getController();

                    controller.setId(info[0]);
                    controller.setName(info[1], info[2]);
                    roomMenu.getChildren().add(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showMatchFoundDialog() {
        Optional<ButtonType> confirmation = matchFoundAlert.showAndWait();

        if (confirmation.get() == acceptButton) {
            Client.sendData("ACCEPT_MATCHMAKING:Accept");
        } else {
            Client.sendData("DECLINE_MATCHMAKING:Decline");
        }

        waitForAnotherAlert.show();
    }

    private void showConfirmExitDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to exit?");
        alert.setTitle("EXIT");

        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.get() == ButtonType.OK) {
            System.exit(1);
        }
    }

    public void closeAllDialog() {
        matchFoundAlert.close();
        waitForAnotherAlert.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        matchFoundAlert.initOwner(stage);
        waitForAnotherAlert.initOwner(stage);
    }

    public void goToBoardAndPlay(String opponentId, String opponentName, String entryPlayerId) {
        BoardController controller = (BoardController) changeScene("/caroclient/Board.fxml");

        controller.startGame(opponentId, opponentName);
        controller.changeTurn(entryPlayerId);
    }

    public void goToBoardAndSpectate(String p1Name, String p1Id, String p2Name, String p2Id, String entryPlayerId,
            String[] moves) {
        BoardController controller = (BoardController) changeScene("/caroclient/Board.fxml");

        controller.startSpectating(p1Name, p1Id, p2Name, p2Id, moves);
        controller.changeTurn(entryPlayerId);
    }
}
