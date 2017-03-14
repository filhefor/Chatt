package gu;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;


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
	
	public void updateChat(Message o) {
		Message message = o;
		boolean recieve = false;
		if(o.getRecipients().length <= 0 || o.getSender().equals(username)){
			recieve = true;
		}else{
			String[] arr = o.getRecipients();
			for(int i = 0; i < arr.length; i++){
				if(arr[i].equals(username)){
					recieve = true;
				}
			}
		}
		
		System.out.println(recieve);
		if(message.getType().equals("usernameList")) {
			updateUsers(message.getUsernameList());
		}
		if(message.getType().equals("message") && recieve == true) {
			viewer.updateChatText(o.getSender(), message.getMessage());
		}
		else if (message.getType().equals("image") && recieve == true ) {
			Image image = o.getImage().getImage().getScaledInstance(320, 210,
					java.awt.Image.SCALE_SMOOTH);
			JLabel label = new JLabel(new ImageIcon(image));
			viewer.updateChatImage(o.getSender(), label);
		}
    }
	
	public void updateUsers(String[] strings){
		viewer.updateUsers(strings);
	}


	public void sendObject(Message o) {
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
