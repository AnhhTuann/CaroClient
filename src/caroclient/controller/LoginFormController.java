/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import caroclient.Client;
import caroclient.handler.LoginFormHandler;

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
        handler = new LoginFormHandler(this);
        Client.registerHandler(handler);
    }

    @FXML
    public void submit(MouseEvent event) {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (email.equals("") || password.equals("")) {
            showErrorDialog("Please provide information!");
        } else {
            Client.sendData("LOGIN:" + email + ";" + password);
        }
    }

    @FXML
    public void goToRegister() {
        changeScene("/caroclient/RegisterForm.fxml");
    }

    public void showErrorDialog(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Failed!");
        alert.setContentText(error);
        alert.initOwner(stage);
        alert.showAndWait();
    }

}
