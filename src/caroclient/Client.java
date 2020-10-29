package caroclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import caroclient.handler.HandlerBase;

public class Client {
	private static int port;
	private static String host;
	private static String id;
	private static Socket socket = null;
	private static BufferedWriter out;
	private static BufferedReader in;
	private static HandlerBase handler;

	public static void setId(String id) {
		Client.id = id;
	}

	public static String getId() {
		return id;
	}

	public static void setPort(int port) {
		Client.port = port;
	}

	public static void setHost(String host) {
		Client.host = host;
	}

	private static void listen() {
		while (true) {
			try {
				String[] response = in.readLine().split(":");
				handler.handleResponse(response[0], response[1].split(";"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createClient() {
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
		Client.handler = handler;

		if (socket == null) {
			createClient();
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
}
