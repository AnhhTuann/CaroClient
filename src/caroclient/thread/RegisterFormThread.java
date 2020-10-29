package caroclient.thread;

import caroclient.controller.RegisterFormController;
import javafx.application.Platform;

public class RegisterFormThread extends BaseThread {
	private RegisterFormController ui;

	public RegisterFormThread(RegisterFormController ui) {
		this.ui = ui;
	}

	@Override
	public void handleResponse(String command, String[] data) {
		switch (command) {
			case "REG_ERR": {
				Platform.runLater(() -> {
					ui.showErrorDialog(data[0]);
				});
				break;
			}
			case "REG_OK": {
				Platform.runLater(() -> {
					ui.showSuccessDialog();
				});
				break;
			}
		}
	}
}
