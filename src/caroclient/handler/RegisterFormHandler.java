package caroclient.handler;

import caroclient.controller.RegisterFormController;
import javafx.application.Platform;

public class RegisterFormHandler extends HandlerBase {
	private RegisterFormController ui;

	public RegisterFormHandler(RegisterFormController ui) {
		this.ui = ui;
	}

	@Override
	public void handleResponse(String command, String[] data) {
		switch (command) {
			case "REGISTER_ERROR": {
				Platform.runLater(() -> {
					ui.showErrorDialog(data[0]);
				});
				break;
			}
			case "REGISTER_OK": {
				Platform.runLater(() -> {
					ui.showSuccessDialog();
				});
				break;
			}
		}
	}
}
