package caroclient;

import java.io.IOException;
import javafx.application.Platform;

public class GameStateReadThread implements Runnable {
	private String response = "";
	private BoardController ui;

	private class OnNewMove implements Command {
		@Override
		public void execute(String data) {
			String[] parts = data.split(";");
			int col = Integer.parseInt(parts[0]);
			int row = Integer.parseInt(parts[1]);
			String fromPlayer = parts[2];

			Platform.runLater(() -> {
				ui.drawMove(col, row, fromPlayer);
			});
		}
	}

	private class OnEndGame implements Command {
		@Override
		public void execute(String data) {
			Platform.runLater(() -> {
				ui.showEndGameDialog(Client.getInstance().getId().equals(data));
				Platform.exit();
				System.exit(1);
			});
		}
	}

	private class OnConnection implements Command {
		@Override
		public void execute(String data) {
			Client.getInstance().setId(data);
		}
	}

	public GameStateReadThread(BoardController ui) {
		this.ui = ui;
	}

	@Override
	public void run() {
		while (true) {
			try {
				response = Client.getInstance().getReader().readLine();

				on("MOV", new OnNewMove());
				on("CON", new OnConnection());
				on("END", new OnEndGame());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private void on(String command, Command handler) {
		String[] requestParts = response.split(":");
		if (requestParts[0].equals(command)) {
			handler.execute(requestParts[1]);
		}
	}
}
