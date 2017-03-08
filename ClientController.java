package gu;

import java.awt.Dimension;
import java.util.Observable;

import javax.swing.*;


public class ClientController extends Observable{
	private Client client;
	private Viewer viewer = new Viewer(this);

	public ClientController(Client client) {
		System.out.println("controller konstruktor");
		this.client = client;
		client.setController(this);
		showGUI();
		//client.getData();
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

	public void sendObject(Object o) {
//		setChanged();
//		notifyObservers(o);
		client.setOkToSend(true);
		client.setObjectToSend(o);

	}
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 1337, "filhefor");
		new ClientController(client);
	}
}
