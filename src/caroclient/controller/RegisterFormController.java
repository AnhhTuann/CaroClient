/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import caroclient.Client;
import caroclient.handler.RegisterFormHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RegisterFormController extends ControllerBase {
    @FXML
    private VBox registerForm;
    private AccountInfoFormController accountInfoFormController;
    private PasswordFormController passwordFormController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new RegisterFormHandler(this);
        Client.registerHandler(handler);

        loadForm();
    }

    private void loadForm() {
        try {
            FXMLLoader accountInfoFormLoader = new FXMLLoader(
                    getClass().getResource("/caroclient/AccountInfoForm.fxml"));
            FXMLLoader passwordFormLoader = new FXMLLoader(getClass().getResource("/caroclient/PasswordForm.fxml"));
            Node accountInfoForm = accountInfoFormLoader.load();
            Node passwordForm = passwordFormLoader.load();
            accountInfoFormController = accountInfoFormLoader.getController();
            passwordFormController = passwordFormLoader.getController();

            registerForm.getChildren().clear();
            registerForm.getChildren().add(accountInfoForm);
            registerForm.getChildren().add(passwordForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showErrorDialog(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Register Failed!");
        alert.setContentText(error);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void showSuccessDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Done!");
        alert.setContentText("Registration Completed!");
        alert.initOwner(stage);
        alert.showAndWait();

        goToLogin();
    }

    @FXML
    public void goToLogin() {
        changeScene("/caroclient/LoginForm.fxml");
    }

    @FXML
    public void submit(MouseEvent event) {
        String accountInfoError = accountInfoFormController.validate();
        String passwordError = passwordFormController.validate();

        if (!accountInfoError.equals("")) {
            showErrorDialog(accountInfoError);
        } else if (!passwordError.equals("")) {
            showErrorDialog(passwordError);
        } else {
            String data = String.join(";", accountInfoFormController.toString(), passwordFormController.toString());
            Client.sendData("REGISTER:" + data);
        }
    }
}
