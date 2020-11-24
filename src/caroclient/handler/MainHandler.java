package caroclient.handler;

import caroclient.Client;
import caroclient.controller.MainController;
import javafx.application.Platform;

public class MainHandler extends HandlerBase {
	private MainController ui;

	public MainHandler(MainController ui) {
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
				Client.sendData("NEW_MATCH:Start new match");

				Platform.runLater(() -> {
					ui.closeAllDialog();
					ui.playGame(opponentInfo[0], opponentInfo[1], data[1]);
				});

				break;
			}
			case "GAME_NOT_FOUND": {
				Client.sendData("REFRESH:Refresh game list");
				break;
			}
			case "GAME_INFO": {
				String[] p1 = data[0].split(",");
				String[] p2 = data[1].split(",");
				String[] moves = data[3].split(",");

				Platform.runLater(() -> {
					ui.spectateGame(p1[1], p1[0], p2[1], p2[0], data[2], moves);
				});

				break;
			}
		}
	}
}
