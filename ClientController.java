package gu;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.*;


public class ClientController extends Observable{
	private Client client;
	private Viewer viewer = new Viewer(this);
	private String username;

	public ClientController() {
		System.out.println("controller konstruktor");
		showGUI();
		username = JOptionPane.showInputDialog("Välj användarnamn");
		Client client = new Client("127.0.0.1", 1337, username, this);
		this.client = client;
	}

	public void showGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(viewer);
				frame.pack();
				frame.setMinimumSize(new Dimension(600,500));
				frame.setVisible(true);
			}
		});
	}
	public void updateChat(Object o) {
		Message message = (Message) o;
		if(message.getType().equals("usernameList")) {
			updateUsers(message.getUsernameList());
		}
		else if(message.getType().equals("message")) {
			viewer.updateChat(message);
		}
//		if(o instanceof String){
//			String stringMessage = (String)o;
//			viewer.updateChat(stringMessage);
//		}else if(o instanceof ImageIcon){
//			ImageIcon imageMessage = (ImageIcon)o;
//			viewer.updateChat(imageMessage);
//		}
		
	}
	
	public void updateUsers(ArrayList<String> usernameList){
		viewer.updateUsers(usernameList);
	}

	public void sendObject(Object o) {
		client.setOkToSend(true);
		client.setObjectToSend(o);

	}
	public void createImageMessage(String sender, String[] recipients, ImageIcon image){
		new Message(sender, recipients, image);
	}
	public void createTextMessage(String sender, String[] recipients, String message){
		new Message(sender,recipients,message);
	}
	
	public String getUsername() {
		return username;
	}
	
	public static void main(String[] args) {
		new ClientController();
	}
}
