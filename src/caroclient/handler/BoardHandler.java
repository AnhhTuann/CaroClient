package caroclient.handler;

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
			case "MOVE": {
				int col = Integer.parseInt(data[0]);
				int row = Integer.parseInt(data[1]);
				String currentPlayerId = data[2];
				String nextPlayerId = data[3];

				Platform.runLater(() -> {
					ui.drawMove(col, row, currentPlayerId);
					ui.changeTurn(nextPlayerId);
				});

				break;
			}
			case "GAMEOVER": {
				Platform.runLater(() -> {
					ui.stopAllTimer();
					ui.showGameOverDialog(data[0]);
				});

				break;
			}
		}
	}
}
