/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import caroclient.Client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LoginFormController extends ControllerBase {

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void submit(MouseEvent event) {
        Client.sendData("LOGIN:" + emailTextField.getText() + ";" + passwordField.getText());
    }

    public void showErrorDialog(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Failed!");
        alert.setContentText(error);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void showSuccessDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Welcome!");
        alert.setContentText("Welcome!");
        alert.initOwner(stage);
        alert.showAndWait();
    }

}
