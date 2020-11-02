package caroclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import caroclient.handler.HandlerBase;
import caroclient.model.Account;

public class Client {
	private static String id;
	private static Socket socket = null;
	private static BufferedWriter out;
	private static BufferedReader in;
	private static Map<String, HandlerBase> handlers = new HashMap<>();
	private static Account account;

	public static void setId(String id) {
		Client.id = id;
	}

	public static String getId() {
		return id;
	}

	private static void listen() {
		while (true) {
			try {
				String[] response = in.readLine().split(":");

				for (HandlerBase handler : handlers.values()) {
					handler.handleResponse(response[0], response[1].split(";"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void connect(String host, int port) {
		try {
			socket = new Socket(host, port);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			Runnable runnable = () -> {
				listen();
			};
			Thread thread = new Thread(runnable);
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void registerHandler(HandlerBase handler) {
		String handlerName = handler.getClass().getSimpleName();
		if (!handlers.containsKey(handlerName)) {
			handlers.put(handlerName, handler);
		}
	}

	public static void sendData(String data) {
		try {
			out.write(data + "\n");
			out.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void setAccount(Account account) {
		Client.account = account;
	}

	public static Account getAccount() {
		return account;
	}
}
