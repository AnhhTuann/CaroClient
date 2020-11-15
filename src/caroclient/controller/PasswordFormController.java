/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class PasswordFormController extends ControllerBase {
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField comfirmPasswordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public String validate() {
        String password = passwordField.getText();
        String confirmPassword = comfirmPasswordField.getText();
        String errorText = "";

        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        if (!passwordPattern.matcher(password).find()) {
            errorText = "Password must be from 8 characters,\nat least one uppercase letter, one lowercase letter and one number!";
        } else if (!password.equals(confirmPassword)) {
            errorText = "Passwords do not match!";
        }

        return errorText;
    }

    public String toString() {
        return passwordField.getText();
    }
}
