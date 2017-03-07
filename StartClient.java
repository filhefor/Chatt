package gu;

import java.io.IOException;

import javax.swing.JOptionPane;
import java.net.*;

public class StartClient {
	
	public StartClient(){
		//String username = JOptionPane.showInputDialog("Välj ett användarnamn");
		Client client;
		try {
			client = new Client("127.0.0.1", 3672, "bög");
			new Controller(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		//new StartClient();
		//Client client;
		try{
			//client = new Client("127.0.0.1", 3672, "bög");
			//new Controller(client);
			new Controller(new Client("127.0.0.1",3520, "bög"));
		}catch(IOException e){}
	}
}
