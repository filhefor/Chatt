package gu;

import javax.swing.*;


public class ClientController {
	private Client client;
	private Viewer gui = new Viewer(this);

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
				frame.add(gui);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

	public void updateChat(Object o) {
		if(o instanceof String){
			String stringMessage = (String)o;
			gui.updateChat(stringMessage);
		}else if(o instanceof ImageIcon){
			ImageIcon imageMessage = (ImageIcon)o;
			gui.updateChat(imageMessage);
		}
		
	}

	public void sendObject(Object o) {
		client.setOkToSend(true);
		client.setObjectToSend(o);

		// try {
		// client.sendMessage(o);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
