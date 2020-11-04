package caroclient.handler;

import caroclient.Client;
import caroclient.controller.HubController;
import javafx.application.Platform;

public class HubHandler extends HandlerBase {
	private HubController ui;

	public HubHandler(HubController ui) {
		this.ui = ui;
	}

	@Override
	public void handleResponse(String command, String[] data) {
		switch (command) {
			case "MATCHMAKING": {
				Client.sendData("NOTHING:Nothing");
				Platform.runLater(() -> {
					ui.showMatchFoundDialog();
				});
				break;
			}
			case "BACK_TO_QUEUE": {
				Platform.runLater(() -> {
					ui.closeAllDialog();
					Client.sendData("READY:" + Client.getAccount().getId());
				});
				break;
			}
			case "NEW_MATCH": {
				Platform.runLater(() -> {
					ui.closeAllDialog();
					ui.changeScene("/caroclient/Board.fxml");
				});
			}
		}
	}
}
