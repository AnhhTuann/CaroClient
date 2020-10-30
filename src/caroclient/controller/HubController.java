/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import caroclient.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        // TODO
    }

    public void setAccount(Account account) {
        this.account = account;
        playerNameLabel.setText(this.account.getFullname());
    }
}
