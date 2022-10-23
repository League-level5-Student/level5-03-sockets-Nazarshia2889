package _02_Chat_Application;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Server {
	private int port;

	private ServerSocket server;
	private Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;
	
	ChatApp chatApp;

	public Server(int port, ChatApp chatApp) {
		this.port = port;
		this.chatApp = chatApp;
	}

	public void start(){
		try {
			server = new ServerSocket(port, 100);

			connection = server.accept();

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

			while (connection.isConnected()) {
				try {
					String message = (String) is.readObject();
					JOptionPane.showMessageDialog(null, message);
					String input = chatApp.history + "\n" +  "Client: " + message;
					chatApp.label.setText(input);
					System.out.println(message);
					
				}catch(EOFException e) {
					JOptionPane.showMessageDialog(null, "Connection Lost");
					System.exit(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "ERROR!!!!!";
		}
	}

	public int getPort() {
		return port;
	}

	public void sendMessage(String message) {
		try {
			if (os != null) {
				os.writeObject(message);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
