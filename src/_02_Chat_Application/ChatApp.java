package _02_Chat_Application;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp extends JFrame{
	JButton button = new JButton("SEND");
	JTextField text = new JTextField(20);
	JPanel panel = new JPanel();
	String history = "";
	JLabel label = new JLabel(history);
	
	Server server;
	Client client;
	
	
	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp(){
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			server = new Server(8080, this);
			setTitle("SERVER");
			JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			button.addActionListener((e)->{
				server.sendMessage(text.getText());
				label.setText(history + "\nServer: " + text.getText());
			});
			setSize(600, 100);
			text.setSize(50, 25);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(text);
			panel.add(button);
			panel.add(label);
			add(panel);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			server.start();
			
		}else{
			setTitle("CLIENT");
			String ipStr = JOptionPane.showInputDialog("Enter the IP Address");
			String prtStr = JOptionPane.showInputDialog("Enter the port number");
			int port = Integer.parseInt(prtStr);
			client = new Client(ipStr, port, this);
			button.addActionListener((e)->{
				client.sendMessage(text.getText());
				label.setText(history + "\nClient: " + text.getText());
			});
			setSize(600, 100);
			text.setSize(50, 25);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(text);
			panel.add(button);
			panel.add(label);
			add(panel);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.start();
		}
	}
}
