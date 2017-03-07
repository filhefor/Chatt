package gu;

import java.io.*;

import java.net.*;

public class Client {
	private Controller controller;
	private String username;
	private Socket socket;
	private Object objectToSend;
	private boolean okToSend = false;
	private String ip;
	private int port;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public Client(String ip, int port, String username) throws UnknownHostException, IOException {
		System.out.println("Client konstruktor");
		this.username = username;
		this.ip = ip;
		this.port = port;
		new Connection(ip, port).start();
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

	public void setController(Controller controller) {
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

	private class Connection extends Thread {
		private String ip;
		private int port;

		public Connection(String ip, int port) {
			this.ip = ip;
			this.port = port;
		}

		public void run() {
			Object outputObject, inputObject;

			try (Socket socket = new Socket(ip, port);
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
				while (!Thread.interrupted()) {
					System.out.println("inne i try sats i klient");
					if (isOkToSend()) {
						output.writeObject(objectToSend);
						output.flush();
					}
					inputObject = input.readObject();
					// inputObject = input.readObject();
					controller.updateChat(inputObject);

				}
				// outputObject = output.writeObject(bajs);
			} catch (IOException e) {

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public Object getObjectToSend() {
		return objectToSend;
	}

	public void setObjectToSend(Object objectToSend) {
		// okToSend = true;
		this.objectToSend = objectToSend;

	}

	public boolean isOkToSend() {
		return okToSend;
	}

	public void setOkToSend(boolean okToSend) {
		this.okToSend = okToSend;
	}

}
