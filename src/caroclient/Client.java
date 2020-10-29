package caroclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private static int port;
	private static String host;
	private Socket socket;
	private BufferedWriter out;
	private BufferedReader in;
	private String id;

	private static class ClientHolder {
		static final Client INSTANCE = new Client();
	}

	private Client() {
		try {
			socket = new Socket(host, port);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public BufferedReader getReader() {
		return in;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public static void setPort(int port) {
		Client.port = port;
	}

	public static void setHost(String host) {
		Client.host = host;
	}

	public static Client getInstance() {
		return ClientHolder.INSTANCE;
	}

	public void sendData(String data) {
		try {
			out.write(data + "\n");
			out.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
