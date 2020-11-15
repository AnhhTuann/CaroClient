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
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class AccountInfoFormController extends ControllerBase {
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField fullnameTextField;
    @FXML
    private DatePicker birthdayDatePicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderChoiceBox.getItems().addAll("Male", "Female", "Others");
        genderChoiceBox.setValue("Male");
    }

    private String getGender() {
        String selectedGender = genderChoiceBox.getValue();
        String gender = "";

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

        return gender;
    }

    public String validate() {
        String email = emailTextField.getText();
        String fullname = fullnameTextField.getText();
        LocalDate birthday = birthdayDatePicker.getValue();
        String gender = getGender();
        Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        LocalDate currentDate = LocalDate.now();
        String errorText = "";

        if (!emailPattern.matcher(email).find()) {
            errorText = "Invalid email address!";
        } else if (fullname.equals("")) {
            errorText = "Full name is required!";
        } else if (gender.equals("")) {
            errorText = "Gender is required!";
        } else if (birthday == null) {
            errorText = "Birthday is required!";
        } else if (Period.between(birthday, currentDate).getYears() < 6) {
            errorText = "You must be 6 or older!";
        }

        return errorText;
    }

    public String toString() {
        String gender = getGender();
        String email = emailTextField.getText();
        String fullname = fullnameTextField.getText();
        String birthday = birthdayDatePicker.getValue().toString();

        return String.join(";", email, fullname, gender, birthday);
    }

    public void setDefaultValue() {
        emailTextField.setText(Client.getAccount().getEmail());
        fullnameTextField.setText(Client.getAccount().getFullname());
        birthdayDatePicker.setValue(LocalDate.parse(Client.getAccount().getBirthday()));

        switch (Client.getAccount().getGender()) {
            case 1: {
                genderChoiceBox.setValue("Male");
                break;
            }
            case 2: {
                genderChoiceBox.setValue("Female");
                break;
            }
            case 3: {
                genderChoiceBox.setValue("Others");
                break;
            }
        }
    }
}
