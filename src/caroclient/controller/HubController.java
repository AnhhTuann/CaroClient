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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class HubController extends ControllerBase {
    @FXML
    private Label playerNameLabel;
    ButtonType acceptButton = new ButtonType("Accept", ButtonData.OK_DONE);
    ButtonType declineButton = new ButtonType("Decline", ButtonData.CANCEL_CLOSE);
    private Alert matchFoundAlert = new Alert(AlertType.CONFIRMATION,
            "We have found for you an opponent! Please accept!", acceptButton, declineButton);
    private Alert waitForAnotherAlert = new Alert(AlertType.NONE, "Waiting for another to response...");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Client.registerHandler(new HubHandler(this));
        playerNameLabel.setText(Client.getAccount().getFullname());
        Client.sendData("RDY:" + Client.getAccount().getId());

        matchFoundAlert.setTitle("Found a match!");
        waitForAnotherAlert.setTitle("Found a match!");
        waitForAnotherAlert.setResult(ButtonType.OK);
    }

    public void showMatchFoundDialog() {
        Optional<ButtonType> confirmation = matchFoundAlert.showAndWait();

        if (confirmation.get() == acceptButton) {
            Client.sendData("MMK_ACP:Accept");
        } else {
            Client.sendData("MMK_DEC:Decline");
        }

        waitForAnotherAlert.show();
    }

    public void goToGameBoard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/Board.fxml"));
            Parent root = loader.load();
            BoardController controller = loader.getController();
            Scene scene = new Scene(root);

            controller.setStage(stage);
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
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
}
