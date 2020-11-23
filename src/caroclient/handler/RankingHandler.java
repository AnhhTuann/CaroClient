package caroclient.handler;

import caroclient.controller.RankingController;
import javafx.application.Platform;

public class RankingHandler extends HandlerBase {
	private RankingController ui;

	public RankingHandler(RankingController ui) {
		this.ui = ui;
	}

	@Override
	public void handleResponse(String command, String[] data) {
		switch (command) {
			case "RANK": {
				Platform.runLater(() -> {
					ui.showRank(data);
				});

				break;
			}
		}
	}
}
