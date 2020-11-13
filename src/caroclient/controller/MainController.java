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
import caroclient.handler.MainHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class MainController extends ControllerBase {
    @FXML
    private VBox container;
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
        handler = new MainHandler(this);
        
        Client.registerHandler(handler);
        Client.sendData("READY:" + Client.getAccount().getId());
        matchFoundAlert.setTitle("Found a match!");
        waitForAnotherAlert.setTitle("Found a match!");
        waitForAnotherAlert.setResult(ButtonType.OK);

        loadHub();
    }

    public void loadHub() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/Hub.fxml"));
            Node item = loader.load();
            HubController controller = loader.getController();

            controller.setContainer(this);
            container.getChildren().clear();
            container.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAccountInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/AccountInfo.fxml"));
            Node item = loader.load();
            AccountInfoController controller = loader.getController();

            controller.setContainer(this);
            container.getChildren().clear();
            container.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void playGame(String opponentId, String opponentName, String entryPlayerId) {
        BoardController controller = (BoardController) changeScene("/caroclient/Board.fxml");

        controller.startGame(opponentId, opponentName);
        controller.changeTurn(entryPlayerId);
    }

    public void spectateGame(String p1Name, String p1Id, String p2Name, String p2Id, String entryPlayerId,
            String[] moves) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/Hub.fxml"));
            Node item = loader.load();
            BoardController controller = loader.getController();

            container.getChildren().clear();
            container.getChildren().add(item);
            controller.startSpectating(p1Name, p1Id, p2Name, p2Id, moves);
            controller.changeTurn(entryPlayerId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
