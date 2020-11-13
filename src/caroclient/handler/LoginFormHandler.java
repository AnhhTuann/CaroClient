package caroclient.handler;

import caroclient.Client;
import caroclient.controller.LoginFormController;
import caroclient.model.Account;
import javafx.application.Platform;

public class LoginFormHandler extends HandlerBase {
    LoginFormController ui;

    public LoginFormHandler(LoginFormController ui) {
        this.ui = ui;
    }

    @Override
    public void handleResponse(String command, String[] data) {
        switch (command) {
            case "LOGIN_ERROR": {
                Platform.runLater(() -> {
                    ui.showErrorDialog(data[0]);
                });
                break;
            }
            case "LOGIN_OK": {
                Account acc = new Account(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4]);

                Client.setAccount(acc);
                Platform.runLater(() -> {
                    ui.changeScene("/caroclient/Main.fxml");
                });
                break;
            }
        }
    }
}
