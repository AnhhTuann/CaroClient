/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import caroclient.Client;
import caroclient.handler.HubHandler;
import caroclient.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class HubController extends ControllerBase {
    private Account account;
    @FXML
    private Label playerNameLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Client.registerHandler(new HubHandler(this));
    }

    public void setAccount(Account account) {
        this.account = account;
        playerNameLabel.setText(this.account.getFullname());
    }

    public void showFoundMatchDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Match found!");
        alert.setContentText("We have found for you an opponent! Please accept!");
        alert.initOwner(stage);
        alert.showAndWait();
    }
}
