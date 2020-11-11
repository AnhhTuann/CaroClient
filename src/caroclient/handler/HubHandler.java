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
				String[] opponentInfo = data[0].split(",");

				Platform.runLater(() -> {
					ui.closeAllDialog();
					ui.goToBoardAndPlay(opponentInfo[0], opponentInfo[1], data[1]);
				});

				break;
			}
			case "GAME_LIST": {
				Platform.runLater(() -> {
					ui.listGameRoom(data);
				});

				break;
			}
			case "GAME_INFO": {
				String[] p1 = data[0].split(",");
				String[] p2 = data[1].split(",");
				String[] moves = data[3].split(",");

				Platform.runLater(() -> {
					ui.goToBoardAndSpectate(p1[1], p1[0], p2[1], p2[0], data[2], moves);
				});

				break;
			}
		}
	}
}
