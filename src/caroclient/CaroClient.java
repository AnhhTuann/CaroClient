/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient;

import caroclient.controller.ControllerBase;
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
        Runtime.getRuntime().addShutdownHook(new ShutDownHook());

        Client.connect("127.0.0.1", 3000);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        Parent root = loader.load();
        ControllerBase controller = loader.getController();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest((event) -> {
            Client.stop();
        });
        stage.show();
        controller.setStage(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
