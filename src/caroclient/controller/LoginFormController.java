/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import caroclient.Client;
import caroclient.handler.LoginFormHandler;
import caroclient.handler.RegisterFormHandler;
import caroclient.model.Account;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Client.registerHandler(new LoginFormHandler(this));
    }

    @FXML
    public void submit(MouseEvent event) {
        Client.sendData("LOGIN:" + emailTextField.getText() + ";" + passwordField.getText());
    }

    @FXML
    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/RegisterForm.fxml"));
            Parent root = loader.load();
            RegisterFormController controller = loader.getController();
            Scene scene = new Scene(root);

            controller.setStage(stage);
            Client.registerHandler(new RegisterFormHandler(controller));
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void goToHub(Account account) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/Hub.fxml"));
            Parent root = loader.load();
            HubController controller = loader.getController();
            Scene scene = new Scene(root);

            controller.setStage(stage);
            controller.setAccount(account);
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showErrorDialog(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Failed!");
        alert.setContentText(error);
        alert.initOwner(stage);
        alert.showAndWait();
    }

}
