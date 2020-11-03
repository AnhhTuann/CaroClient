package caroclient.handler;

import caroclient.Client;
import caroclient.controller.BoardController;
import javafx.application.Platform;

public class BoardHandler extends HandlerBase {
	private BoardController ui;

	public BoardHandler(BoardController ui) {
		this.ui = ui;
	}

	@Override
	public void handleResponse(String command, String[] data) {
		switch (command) {
			case "MOV": {
				System.out.println("New move");
				int col = Integer.parseInt(data[0]);
				int row = Integer.parseInt(data[1]);

				Platform.runLater(() -> {
					ui.drawMove(col, row, data[2]);
				});
				break;
			}
			case "END": {
				Platform.runLater(() -> {
					ui.showGameOverDialog(Client.getAccount().getId().equals(data[0]));
				});

				break;
			}
		}
	}
}
