package gu;

import java.awt.Dimension;
import java.util.Observable;

import javax.swing.*;


public class ClientController extends Observable{
	private Client client;
	private Viewer viewer = new Viewer(this);

	public ClientController() {
		System.out.println("controller konstruktor");
		showGUI();
		String username = JOptionPane.showInputDialog("Välj användarnamn");
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
		if(o instanceof String){
			String stringMessage = (String)o;
			viewer.updateChat(stringMessage);
		}else if(o instanceof ImageIcon){
			ImageIcon imageMessage = (ImageIcon)o;
			viewer.updateChat(imageMessage);
		}
		
	}
	
	public void updateUsers(String user){
		viewer.updateUsers(user);
	}

	public void sendObject(Object o) {
		client.setOkToSend(true);
		client.setObjectToSend(o);

	}
	
	public static void main(String[] args) {
		new ClientController();
	}
}
