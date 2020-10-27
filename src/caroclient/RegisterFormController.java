/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class RegisterFormController implements Initializable {

    @FXML
    private ChoiceBox<String> genderSelection;
    @FXML
    private TextField emailTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextField confirmPasswordTxt;
    @FXML
    private TextField fullnameTxt;
    @FXML
    private DatePicker birthdayDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderSelection.getItems().addAll("Male", "Female", "Others");
    }    
    
    @FXML
    public void submit(MouseEvent event) {
        String email = emailTxt.getText();
        String password = passwordTxt.getText();
        String confirmPassword = confirmPasswordTxt.getText();
        String fullname = fullnameTxt.getText();
        String gender = genderSelection.getValue();
        LocalDate birthday = birthdayDate.getValue();
        String data = String.join(";", email, password, fullname, gender, birthday.toString());
        Client.getInstance().sendData("REG:" + data);
    }
}
