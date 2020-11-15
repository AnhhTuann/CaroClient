package caroclient.controller;

import java.io.IOException;

import caroclient.Client;
import caroclient.handler.HandlerBase;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class ControllerBase implements Initializable {
	protected Stage stage;
	protected HandlerBase handler;

	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.sizeToScene();
	}

	public ControllerBase changeScene(String sceneName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
			Parent root = loader.load();
			ControllerBase controller = loader.getController();
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("/caroclient/styles.css").toExternalForm());
			controller.setStage(stage);
			stage.setScene(scene);
			Client.unregisterHandler(handler);

			return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
