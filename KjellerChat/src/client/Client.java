package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.*;
import org.json.*;

public class Client {
	
	private final static String host = "25.122.70.3";
	private final static int port = 9999;
	
	private static Socket clientSocket;
	private static DataOutputStream out;
	private static boolean connected;
	
	public static final boolean debug = false;
	private final MessageParser parser = new MessageParser();
	
	public static void main(String args[])  {
		new Client();
	}
	
	public Client() {
		
		if(debug)
			new ChatWindow(this);
		else
			try {
				connect();
				new MessageReceiver(this);
				new ChatWindow(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void sendMessage(String payload) {
		try {
			out.writeUTF("msg: " + payload);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean login(String uid, String pw) {
		try {
			JSONObject obj = parser.login(uid, pw);
			out.writeUTF(obj.toString());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			if(inFromServer.readLine() == "OK")
				return true;
			System.out.println(inFromServer.readLine());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return false;
		
	}
	
	public static void disconnect() throws IOException {
		out.writeUTF("Mej ser naa");
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.println(inFromServer.readLine());
		clientSocket.close();
	}
	
	public static void connect() throws IOException {
		System.out.println("About to connect");
		clientSocket = new Socket(host, port);
		System.out.println("Connected successfully");
		
		
		out = new DataOutputStream(clientSocket.getOutputStream());
		out.writeUTF("Hewwellloooow zhzhzhew, walcumeeazzhhz ot thfffffis yoootjub tjutorial zhzxczhxz");
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println(inFromServer.readLine());
        connected = true;
	}
	
	public void test(){
		System.out.println("Stuff");
	}
	
	public static boolean isConnected(){
		return connected;
	}
	
	public Socket getSocket() {
		return clientSocket;
	}
	
}
