package gu;

import java.io.*;
import java.net.*;

public class Server implements Runnable {
	private ServerSocket serverSocket;
	private Thread server = new Thread(this);
	private Object inputObject;

	public Server(int port) throws IOException {
		// serverSocket = new ServerSocket(port);
		// server.start();

		serverSocket = new ServerSocket(port);
		server.start();

	}

	public void run() {
		Object inputObject;
		System.out.println("Servern är igång");
		while (true) {
			try (Socket socket = serverSocket.accept();
					ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
				System.out.println("inne i try sats i server");

				inputObject = input.readObject();

				output.writeObject(inputObject);
				output.flush();

			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private class ConnectionThread extends Thread {

		public void run() {

			while (true) {
				try (Socket socket = serverSocket.accept();
						ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
					inputObject = input.readObject();

					output.writeObject(inputObject);
					output.flush();
				} catch (IOException | ClassNotFoundException e) {
				}
			}

		}
	}

	public static void main(String[] args) throws IOException {
		new Server(3520);
	}

}
