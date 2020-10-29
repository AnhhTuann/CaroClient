/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import caroclient.Client;
import caroclient.thread.RegisterFormThread;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RegisterFormController extends BaseController {
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField fullnameTextField;
    @FXML
    private DatePicker birthdayDatePicker;
    private String email = "";
    private String password = "";
    private String confirmPassword = "";
    private String fullname = "";
    private String gender = "";
    private LocalDate birthday = null;

    private boolean validate() {
        Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        LocalDate currentDate = LocalDate.now();

        if (!emailPattern.matcher(email).find()) {
            showErrorDialog("Invalid email address!");
        } else if (!passwordPattern.matcher(password).find()) {
            showErrorDialog(
                    "Password must be from 8 characters,\nat least one uppercase letter, one lowercase letter and one number!");
        } else if (!password.equals(confirmPassword)) {
            showErrorDialog("Passwords do not match!");
        } else if (fullname.equals("")) {
            showErrorDialog("Full name is required!");
        } else if (gender.equals("")) {
            showErrorDialog("Gender is required!");
        } else if (birthday == null) {
            showErrorDialog("Birthday is required!");
        } else if (Period.between(birthday, currentDate).getYears() < 6) {
            showErrorDialog("You must be 6 or older!");
        } else {
            return true;
        }

        return false;
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
        alert.setContentText("Your account is ready to use!");
        alert.initOwner(stage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderChoiceBox.getItems().addAll("Male", "Female", "Others");
        genderChoiceBox.setValue("Male");

        RegisterFormThread task = new RegisterFormThread(this);
        Thread thread = new Thread(task);
        thread.start();
    }

    @FXML
    public void submit(MouseEvent event) {
        String selectedGender = genderChoiceBox.getValue();

        email = emailTextField.getText();
        password = passwordField.getText();
        confirmPassword = confirmPasswordField.getText();
        fullname = fullnameTextField.getText();
        birthday = birthdayDatePicker.getValue();

        switch (selectedGender) {
            case "Male": {
                gender = "1";
                break;
            }
            case "Female": {
                gender = "2";
                break;
            }
            case "Others": {
                gender = "3";
                break;
            }
        }

        if (validate()) {
            String data = String.join(";", email, password, fullname, gender, birthday.toString());
            Client.getInstance().sendData("REG:" + data);
        }
    }
}
