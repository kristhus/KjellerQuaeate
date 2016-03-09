package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MessageReceiver extends Thread {
	
	private Client client;
	
	public MessageReceiver(Client client) {
		this.client = client;
		start();
	}
	
	
	
	public void run() {
		System.out.println("Thread started");
		while(client.isConnected()) {
			try {
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
				if(inFromServer.readLine() == null) {
					System.out.println("Nothing received, waiting");
					sleep(3000);
					System.out.println("Done waiting");
				}
				else
					System.out.println("From server: " + inFromServer.readLine());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
