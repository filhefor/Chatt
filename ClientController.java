package gu;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
/**
 *  ClientController is the Controller for client handles messages recieved and sent
 * @author Lucas, Elias, David, John, Filip, Alexander
 *
 */

public class ClientController {
	private Client client;
	private Viewer viewer = new Viewer(this);
	private String username;

	/**
	 * Constructor which prompts a InputDialog to choose username.
	 * Then starts client and connects to ip
	 */
	public ClientController() {
		System.out.println("controller konstruktor");

		showGUI();
		username = JOptionPane.showInputDialog("Välj användarnamn");
		client	 = new Client("127.0.0.1", 1337, username, this);
		//client.getData();
	}
	/**
	 * Method to show the GUI
	 */
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
	/**
	 * Method that handles Message objects recieved from server.
	 * Reads type and runs other methods after.
	 * @param o Message to handle
	 */
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
			viewer.updateUsers(message.getUsernameList());
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

	/** 
	 *  runs Method in client which sends Message
	 * @param o Which Message to send
	 */
	public void sendObject(Message o) {
		client.setObjectToSend(o);

	}
	/**
	 * Creates a Message with type Image
	 * @param recipients which Recipients to recieve message
	 * @param image Image to send
	 */
	public void createImageMessage(String[] recipients, ImageIcon image){
		sendObject(new Message(username, recipients, image));
	}
	/**
	 * Creates a MEssage with type Text
	 * @param recipients which Recipients to recieve message
	 * @param message messge to send
	 */
	public void createTextMessage(String[] recipients, String message){
		sendObject(new Message(username,recipients,message));
	}
	/**
	 * Main method tha tstarts client
	 * @param args
	 */
	public static void main(String[] args) {
		new ClientController();
	}
}
