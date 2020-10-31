package caroclient.handler;

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
				Platform.runLater(() -> {
					ui.showFoundMatchDialog();
				});
				break;
			}
		}
	}
}
