package caroclient.handler;

import caroclient.Client;
import caroclient.controller.AccountSummaryController;
import caroclient.model.Account;
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
			case "UPDATE_OK": {
				Account account = Client.getAccount();

				account.setEmail(data[0]);
				account.setFullname(data[2]);
				account.setGender(Integer.parseInt(data[3]));
				account.setBirthday(data[4]);

				Client.setAccount(account);

				Platform.runLater(() -> {
					ui.showSuccessDialog();
				});
				break;
			}
			case "UPDATE_ERROR": {
				Platform.runLater(() -> {
					ui.showErrorDialog(data[0]);
				});
				break;
			}
			case "CHANGE_PASSWORD_ERROR": {
				Platform.runLater(() -> {
					ui.showErrorDialog(data[0]);
				});

				break;
			}
			case "CHANGE_PASSWORD_OK": {
				Platform.runLater(() -> {
					ui.showSuccessDialog();
				});

				break;
			}
		}
	}
}
