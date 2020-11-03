package caroclient.controller;

import caroclient.handler.HandlerBase;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class ControllerBase implements Initializable {
	protected Stage stage;
	protected HandlerBase handler;

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
