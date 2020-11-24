/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class PlayerRankController extends ControllerBase {
    @FXML
    private Label rankLbl;
    @FXML
    private Label playerNameLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setInfo(String rank, String name) {
        rankLbl.setText(rank);
        playerNameLbl.setText(name);
    }
}
