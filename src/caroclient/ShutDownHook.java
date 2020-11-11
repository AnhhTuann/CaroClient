package caroclient;

public class ShutDownHook extends Thread {
	@Override
	public void run() {
		Client.stop();
		super.run();
	}
}
