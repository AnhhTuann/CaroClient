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
import javafx.scene.layout.VBox;

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
    private MainController container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new HubHandler(this);
        Client.registerHandler(handler);

        playerNameLabel.setText(Client.getAccount().getFullname());
        Client.sendData("REFRESH:Refresh game list");
    }

    @FXML
    public void refreshGameList() {
        Client.sendData("REFRESH:Refresh game list");
    }

    @FXML
    public void goToAccountInfo() {
        container.loadAccountInfo();
    }

    @FXML
    void exitGame() {
        showConfirmExitDialog();
    }

    public void setContainer(MainController container) {
        this.container = container;
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

    private void showConfirmExitDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to exit?");
        alert.setTitle("EXIT");

        Optional<ButtonType> confirmation = alert.showAndWait();

        if (confirmation.get() == ButtonType.OK) {
            System.exit(1);
        }
    }
}
