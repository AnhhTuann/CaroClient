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
			case "MMK": {
				Client.sendData("RRN:Rerun stream");
				Platform.runLater(() -> {
					ui.showMatchFoundDialog();
				});
				break;
			}
			case "MMK_BCK": {
				Platform.runLater(() -> {
					ui.closeAllDialog();
					Client.sendData("RDY:Ready");
				});
				break;
			}
			case "MMK_NEW": {
				Platform.runLater(() -> {
					ui.closeAllDialog();
					ui.goToGameBoard();
				});
			}
		}
	}
}
