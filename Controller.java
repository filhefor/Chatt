package gu;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Controller {
	private Client client;
	private Viewer gui = new Viewer(this);
	
	public Controller(Client client) {
		System.out.println("controller konstruktor");
		this.client = client;
		client.setController(this);
		showGUI();
		//client.getData();
	}
	
	public void HEJ(String trosa){
		
		
	}
	
	public void showGUI(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(gui);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	public void updateChat(Object o){
		gui.updateChat(o);
	}
	
	public void sendObject(Object o){
		client.setOkToSend(true);
		client.setObjectToSend(o);
		
//		try {
//			client.sendMessage(o);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
//	public static void main(String[] args) {
//		//Client client = new Client("127.0.0.1", 3672);
//		new Controller(client);
//	}
}
