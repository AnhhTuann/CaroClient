/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient;

import caroclient.controller.RegisterFormController;
import caroclient.handler.RegisterFormHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class CaroClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Client.setHost("127.0.0.1");
        Client.setPort(3000);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterForm.fxml"));
        Parent root = loader.load();
        RegisterFormController controller = loader.getController();
        Scene scene = new Scene(root);

        controller.setStage(stage);
        Client.registerHandler(new RegisterFormHandler(controller));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
