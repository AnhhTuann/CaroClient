package caroclient.thread;

import caroclient.controller.LoginFormController;
import javafx.application.Platform;

public class LoginFormThread extends BaseThread {
    LoginFormController ui;

    public LoginFormThread(LoginFormController ui) {
        this.ui = ui;
    }

    @Override
    public void handleResponse(String command, String[] data) {
        switch (command) {
            case "LOGIN_ERR": {
                Platform.runLater(() -> {
                    ui.showErrorDialog(data[0]);
                });
                break;
            }
            case "LOGIN_OK": {
                Platform.runLater(() -> {
                    ui.showSuccessDialog();
                });
                break;
            }
        }
    }
}
