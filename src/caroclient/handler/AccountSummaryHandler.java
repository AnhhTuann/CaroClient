package caroclient.handler;

import caroclient.controller.AccountSummaryController;
import javafx.application.Platform;

public class AccountSummaryHandler extends HandlerBase {
	private AccountSummaryController ui;

	public AccountSummaryHandler(AccountSummaryController ui) {
		this.ui = ui;
	}

	@Override
	public void handleResponse(String command, String[] data) {
		switch (command) {
			case "ACHIEVEMENT": {
				Platform.runLater(() -> {
					ui.getAchievementData(data[0], data[1], data[2], data[3], data[4], data[5]);
				});

				break;
			}
		}
	}
}
