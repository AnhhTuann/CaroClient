package caroclient.thread;

import java.io.IOException;

import caroclient.Client;

public abstract class BaseThread implements Runnable {
	protected abstract void handleResponse(String command, String[] data);

	@Override
	public void run() {
		try {
			while (true) {
				String response = Client.getInstance().getReader().readLine();
				String[] parts = response.split(":");
				String[] data = parts[1].split(";");

				handleResponse(parts[0], data);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}