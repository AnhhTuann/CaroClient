package caroclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import caroclient.handler.HandlerBase;
import caroclient.model.Account;

public class Client {
	private static String id;
	private static Socket socket;
	private static BufferedWriter out;
	private static BufferedReader in;
	private static ArrayList<HandlerBase> handlers = new ArrayList<>();
	private static Account account;
	private static boolean isExit = false;
	private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

	public static String getId() {
		return id;
	}

	private static void listen() {
		while (!isExit) {
			try {
				String response = in.readLine().replace("\n", "").replace("\r", "");
				String[] plainResponse = response.split(":");

				if (plainResponse[0].equals("CONNECTED")) {
					id = plainResponse[1];
					encryptor.setPassword(id);
				} else {
					String[] decryptedString = encryptor.decrypt(response).split(":");

					for (HandlerBase handler : handlers) {
						handler.handleResponse(decryptedString[0], decryptedString[1].split(";"));
					}
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
		handlers.add(handler);
	}

	public static void unregisterHandler(HandlerBase handler) {
		handlers.remove(handler);
	}

	public static void sendData(String data) {
		try {
			String encrypted = encryptor.encrypt(data);

			out.write(encrypted + "\n");
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

	public static void stop() {
		isExit = true;
		sendData("DISCONNECT:" + Client.getId());

		try {
			socket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
