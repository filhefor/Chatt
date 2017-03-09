package gu;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server implements Runnable {

	private ServerSocket serverSocket;
	private Thread server = new Thread(this);
	private boolean on = true;
	private ArrayList<ClientHandler> list = new ArrayList<ClientHandler>();
	private ArrayList<String> usernameList = new ArrayList<String>();
	private LinkedList<Object> messageList = new LinkedList<Object>();

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		server.start();

	}
	
	public synchronized void sendMessage(Object obj) {
		for(int i = list.size(); --i >= 0;) {
			ClientHandler sendClient = list.get(i);
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
	
	public void removeUser(String username) throws IOException {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).username.equals(username)) {
				list.remove(i);
			}
			newUser();
		}
	}


	public synchronized void newUser() throws IOException {
		usernameList.clear();
		for(int i = 0; i < list.size(); i++) {
			usernameList.add(list.get(i).username);
		}
		Message message = new Message(usernameList);
		for (int i = 0; i < list.size(); i++) {
			ClientHandler sendClient = list.get(i);
			sendClient.writeMessage(message);
		}
	}

	public void run() {
		try {
			while (on) {
				Socket socket = serverSocket.accept();
				ClientHandler newClient = new ClientHandler(socket);
				System.out.println("ClientConnected");
				list.add(newClient);
				newClient.start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < list.size(); i++) {
					ClientHandler clientClose = list.get(i);

					clientClose.close();

				}
			} catch(Exception e) {
			} 
		} catch (IOException e) {
	}
	}

	private class ClientHandler extends Thread {
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private int id;
		private String username;
//		private String message;
		private Object message;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				username = (String) input.readObject();
				usernameList.add(username);
				System.out.println("Tagit emot ett username från ny client: "+username);
				System.out.println("Lagt till "+username+ " till connectedUsers");
				for (int i = 0; i < messageList.size(); i++) {
					writeMessage(messageList.get(i));
					System.out.println(messageList.get(i));
				}
				System.out.println(username + " connected");
			} catch (IOException | ClassNotFoundException e) {
			}
		}

		public void run() {
			try {
				newUser();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			while (true) {
				try {
					message = input.readObject();
					System.out.println(message);
					messageList.add(message);
					sendMessage(message);
				} catch (IOException e) {
					close();
					System.out.println(username + " kopplade ner");
					try {
						removeUser(username);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private synchronized boolean writeMessage(Object obj) throws IOException {
			if(!socket.isConnected()) {
				socket.close();
				return false;
			}
			try {
				System.out.println(obj + "Hej");
				output.writeObject(obj);

				output.flush();
			} catch (IOException e) {

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

		new Server(1337);

	}

}
