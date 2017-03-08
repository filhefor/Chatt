package gu;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.util.LinkedList;

public class Server implements Runnable{

	private ServerSocket serverSocket;
	private Socket connection;
    private Thread server = new Thread(this);
	private Object objectToSend;
	private ThreadPool pool;

	public Server(int port, int nbrOfThreads) throws IOException {
		pool = new ThreadPool(nbrOfThreads);
		serverSocket = new ServerSocket(port);
		pool.start();
		server.start();

	}
	
	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				pool.execute(new ClientHandler(socket));
    		} catch(IOException e) { 
    			System.err.println(e);
    		}
		}
	}

	private class ClientHandler implements Runnable {
		private Socket socket;
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		public void run() {

			Object inputObject;
			System.out.println("ClientHandler runmetod i Server.java är igång");
			while (true) {

				try {
					try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
							ObjectInputStream input = new ObjectInputStream(socket.getInputStream())	) {
						while(true) {
							inputObject = input.readObject();
							System.out.println(inputObject);
							output.writeObject("SERVER- " + inputObject);
							output.flush();
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage() + "NÅGOT ÄR FEL");
				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
		new Server(1337,100);
	}

}
