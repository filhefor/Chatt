package gu;

import java.io.*;

import java.net.*;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
/**
 * Client handles connections from and to the client.
 * @author Lucas, Elias, David, John, Filip, Alexander
 *
 */
public class Client{
	private ClientController controller;
	private String username;
	private Socket socket;
	private Message objectToSend;
	private boolean okToSend = false;
	private String ip;
	private int port;
	private ObjectOutputStream output;
	private ObjectInputStream input;
/**
 * Client constructor that sets ip, port, username and a controller.
 *  Also makes a new instance of serverListener
 * @param ip ip to connect to
 * @param port port to connect to
 * @param username  Username that will be used
 * @param controller ClientController instance
 */
	public Client(String ip, int port, String username, ClientController controller) {
		System.out.println("Client konstruktor");
		this.username = username;
		this.ip = ip;
		this.port = port;
		this.controller = controller;
		new ServerListener(ip, port);
		// connect();
	}
	
	/**
	 * Inner class that handles the input and output streams in a seperate thread
	 * @author Lucas, Elias, David, John, Filip, Alexander
	 *
	 */
	private class ServerListener extends Thread {
		private String ip;
		private int port;
		/**
		 *  Constructor that sets ip and port and opens a new input and output stream.
		 *  Also sends username to server.
		 * @param ip ip to use
		 * @param port port to use
		 */
		public ServerListener(String ip, int port) {
			this.ip = ip;
			this.port = port;
			try {
				socket = new Socket(ip, port);

			} catch (Exception e) {

			}
			try {
				input = new ObjectInputStream(socket.getInputStream());
				output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject(username);
				output.flush();
			} catch (IOException ioe) {

			}
			start();
		}
		/**
		 * Method that recieves messages from server.
		 */
		public void run() {
			Message message;
			while (true) {
				try {
					Date date = new Date();
					message = (Message)input.readObject();
					message.setClientRecieved(date.toString().substring(10, 19));
					System.out.println(message + " från klient");
					System.out.println(controller);
					System.out.println(message.getUsernameList());
					controller.updateChat(message);
				} catch (IOException ioe) {

				} catch (ClassNotFoundException cnfe) {

				}
			}
		}
	}
	/**
	 * sends a Message to the server 
	 * @param objectToSend Message to send
	 */
	public void setObjectToSend(Message objectToSend) {
		this.objectToSend = objectToSend;
		try {
			//System.out.println("objekt: "+this.objectToSend);
			output.writeObject(objectToSend);
			output.flush();

			// objectToSend = null;
		} catch (IOException ioe) {
		}
	}
}
