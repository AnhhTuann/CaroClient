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
import caroclient.handler.AccountSummaryHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class AccountSummaryController extends ControllerBase {
    @FXML
    private Button backButton;
    @FXML
    private Label winIndex;
    @FXML
    private Label loseIndex;
    @FXML
    private Label drawIndex;
    @FXML
    private Label winRateIndex;
    @FXML
    private Label longestWinStreakIndex;
    @FXML
    private Label longestLoseStreakIndex;
    @FXML
    private VBox accountInfoFormContainer;
    @FXML
    private VBox changePasswordFormContainer;
    @FXML
    private Button saveButton;
    @FXML
    private Button changeButton;
    @FXML
    private PasswordField oldPasswordField;
    private MainController container;
    private AccountInfoFormController accountInfoFormController;
    private PasswordFormController changePasswordFormController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new AccountSummaryHandler(this);

        Client.registerHandler(handler);
        Client.sendData("ACHIEVEMENT:" + Client.getAccount().getId());

        loadForm();
        accountInfoFormController.setDefaultValue();
    }

    private void loadForm() {
        try {
            FXMLLoader accountInfoFormLoader = new FXMLLoader(
                    getClass().getResource("/caroclient/AccountInfoForm.fxml"));
            FXMLLoader passwordFormLoader = new FXMLLoader(getClass().getResource("/caroclient/PasswordForm.fxml"));
            Node accountInfoForm = accountInfoFormLoader.load();
            Node passwordForm = passwordFormLoader.load();
            accountInfoFormController = accountInfoFormLoader.getController();
            changePasswordFormController = passwordFormLoader.getController();

            accountInfoFormContainer.getChildren().clear();
            changePasswordFormContainer.getChildren().clear();
            accountInfoFormContainer.getChildren().add(accountInfoForm);
            changePasswordFormContainer.getChildren().add(passwordForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToHub() {
        container.loadHub();
    }

    @FXML
    public void changeInformation() {
        String error = accountInfoFormController.validate();

        if (error.equals("")) {
            Client.sendData("UPDATE:" + accountInfoFormController.toString());
        } else {
            showErrorDialog(error);
        }
    }

    @FXML
    public void changePassword() {
        String oldPassword = oldPasswordField.getText();
        String error = changePasswordFormController.validate();

        if (error.equals("")) {
            Client.sendData(
                    "CHANGE_PASSWORD:" + String.join(";", oldPassword, changePasswordFormController.toString()));
        } else {
            showErrorDialog(error);
        }
    }

    public void getAchievementData(String win, String lose, String draw, String longestWinStreak,
            String longestLoseStreak, String winRate) {
        winIndex.setText(win);
        loseIndex.setText(lose);
        drawIndex.setText(draw);
        winRateIndex.setText(winRate + "%");
        longestLoseStreakIndex.setText(longestLoseStreak);
        longestWinStreakIndex.setText(longestWinStreak);
    }

    public void setContainer(MainController container) {
        this.container = container;
    }

    public void showErrorDialog(String error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void showSuccessDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Completed!");
        alert.initOwner(stage);
        alert.showAndWait();
    }
}
