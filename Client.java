package gu;

import java.io.*;

import java.net.*;
import java.util.Observable;
import java.util.Observer;

public class Client implements Observer {
	private ClientController controller;
	private String username;
	private Socket socket;
	private Object objectToSend;
	private boolean okToSend = false;
	private String ip;
	private int port;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public Client(String ip, int port, String username) {
		System.out.println("Client konstruktor");
		this.username = username;
		this.ip = ip;
		this.port = port;
		new ServerListener(ip, port);
		// connect();
	}

	public void connect() throws UnknownHostException, IOException {
		System.out.println("connect metoden i klient");
		socket = new Socket(ip, port);
		System.out.println("socket klar: " + socket.getLocalAddress() + " " + socket.getPort());
		System.out.println(socket.getOutputStream());
		// try{
		// output = new ObjectOutputStream(socket.getOutputStream());
		// output.flush();
		// input = new ObjectInputStream(socket.getInputStream());
		// //getData();
		// }catch(Exception e){
		// System.out.println("Kunde inte hämta strömmar");
		// }

		System.out.println("Klienten är ansluten till servern");
	}

	public void setController(ClientController controller) {
		this.controller = controller;
	}

	public void sendObject(Object o) {
		objectToSend = o;
	}

	public void getData() {
		Object message;
		while (true) {
			try {
				input = new ObjectInputStream(socket.getInputStream());
				message = input.readObject();
				controller.updateChat(message);
			} catch (IOException | ClassNotFoundException e) {
			}
		}
	}

	public void sendMessage(Object message) throws IOException {
		output = new ObjectOutputStream(socket.getOutputStream());
		output.writeObject(message);
		output.flush();
	}

	private class ServerListener extends Thread {
		private String ip;
		private int port;

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
			} catch (IOException ioe) {

			}
			start();
		}

		public void run() {
			Object message;
			while (true) {

				try {
					message = input.readObject();
					System.out.println(message + " från klient");
					controller.updateChat(message);
				} catch (IOException ioe) {

				} catch (ClassNotFoundException cnfe) {

				}

			}

		}
		// Object outputObject, inputObject;
		// while (true) {
		// try (Socket socket = new Socket(ip, port);
		// ObjectOutputStream output = new
		// ObjectOutputStream(socket.getOutputStream());
		// ObjectInputStream input = new
		// ObjectInputStream(socket.getInputStream())) {
		//
		// System.out.println("inne i while loop");
		// try{
		// output.writeObject(objectToSend);
		// output.flush();
		// objectToSend = null;
		// }catch(Exception e){}
		//
		// // outputObject = output.writeObject(bajs);
		// } catch (IOException e) {}
		// }
		// }
	}

	public Object getObjectToSend() {
		return objectToSend;
	}

	public void setObjectToSend(Object objectToSend) {
		this.objectToSend = objectToSend;
		try {
			//System.out.println("objekt: "+this.objectToSend);
			output.writeObject(objectToSend);
			output.flush();

			// objectToSend = null;
		} catch (IOException ioe) {

		}
	}

	public boolean isOkToSend() {
		return okToSend;
	}

	public void setOkToSend(boolean okToSend) {
		this.okToSend = okToSend;
	}

	@Override
	public void update(Observable o, Object arg) {
		objectToSend = arg;
	}

}
