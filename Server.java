package gu;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private Thread server = new Thread(this);
	private boolean on = true;
	private ArrayList<ClientHandle> list = new ArrayList<ClientHandle>();

	public Server(int port, int nbrOfThreads) throws IOException {
		serverSocket = new ServerSocket(port);
		server.start();

	}
	
	public synchronized void sendMessage(Object obj) {
		for(int i = list.size(); --i >= 0;) {
			ClientHandle sendClient = list.get(i);
			try{
				if(!sendClient.writeMessage(obj)) {
					list.remove(i);
					System.out.println("Disconnected client");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		try {
			while(on) {
			Socket socket = serverSocket.accept();
			ClientHandle newClient = new ClientHandle(socket);
			System.out.println("ClientConnected");
			list.add(newClient);
			newClient.start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < list.size(); i++) {
					ClientHandle clientClose = list.get(i);
					try {
						clientClose.input.close();
						clientClose.output.close();
						clientClose.socket.close();
					} catch(IOException ioe) {
					}
				}
			} catch(Exception e) {
			} 
		} catch (IOException e) {
	}
	}

	private class ClientHandle extends Thread {
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private int id;
		private String username;
		private String message;

		public ClientHandle(Socket socket) {
			this.socket = socket;
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				username = (String) input.readObject();
				System.out.println(username + " connected");
			} catch (IOException | ClassNotFoundException e) {
			}
		}

		public void run() {
			while (true) {
				try {
					message = (String) input.readObject();
					System.out.println(message);
					sendMessage(message);
					
				} catch (IOException | ClassNotFoundException e) {
					break;
				}
			}
		}
		
		private boolean writeMessage(Object obj) throws IOException {
			if(!socket.isConnected()) {
				socket.close();
				return false;
			}
			try {
				output.writeObject(obj);
			} catch(IOException e) {
			}
			return true;
		}

		private void close() {
			try {
				if (output != null)
					output.close();
			} catch (Exception e) {
			}
			try {
				if (input != null)
					input.close();
			} catch (Exception e) {
			}
			try {
				if (socket != null) 
					socket.close();
			} catch (Exception e) {
			}
		}

	}

	public static void main(String[] args) throws IOException {
		new Server(1337,100);
	}

}
