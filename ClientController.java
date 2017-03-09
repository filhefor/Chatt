package gu;

import java.awt.Dimension;

import java.util.ArrayList;

import javax.swing.*;


public class ClientController {
	private Client client;
	private Viewer viewer = new Viewer(this);
	private String username;


	public ClientController() {
		System.out.println("controller konstruktor");

		showGUI();
		username = JOptionPane.showInputDialog("Välj användarnamn");
		client	 = new Client("127.0.0.1", 1337, username, this);
		//client.getData();
	}

	public void showGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(viewer);
				frame.setLocation(350, 150);
				frame.pack();
				frame.setMinimumSize(new Dimension(500,500));
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
    }
	
	public void updateUsers(ArrayList<String> usernameList){
		viewer.updateUsers(usernameList);
	}


	public void sendObject(Object o) {
		client.setOkToSend(true);
		client.setObjectToSend(o);

	}
	public void createImageMessage(String[] recipients, ImageIcon image){
		sendObject(new Message(username, recipients, image));
	}
	public void createTextMessage(String[] recipients, String message){
		sendObject(new Message(username,recipients,message));
	}
	
	public String getUsername() {
		return username;
	}
	
	public void sendImage(String image) {
		
	}
	
	public static void main(String[] args) {
		new ClientController();
	}
}
