/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import caroclient.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class RoomMenuItemController extends ControllerBase {
    @FXML
    private Label roomName;
    private String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void onChoose(MouseEvent event) {
        Client.sendData("SPECTATE:" + id);
    }

    public void setName(String player1, String player2) {
        roomName.setText(player1 + " vs " + player2);
    }

    public void setId(String id) {
        this.id = id;
    }
}
