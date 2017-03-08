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
	private LinkedList<Object> messageList = new LinkedList<Object>();
	private ArrayList<String> connectedUsers = new ArrayList<String>();

	public Server(int port, int nbrOfThreads) throws IOException {
		serverSocket = new ServerSocket(port);
		server.start();

	}

	public synchronized void sendMessage(Object obj) {
		for (int i = list.size(); --i >= 0;) {
			ClientHandler sendClient = list.get(i);
			try {
				if (!sendClient.writeMessage(obj)) {
					list.remove(i);
					System.out.println("Disconnected client");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void newUser(Object obj) {
		for (int i = list.size(); --i >= 0;) {
			ClientHandler sendClient = list.get(i);
			sendClient.updateUsers(obj);
		}
	}

	public void run() {
		try {
			while (on) {
				Socket socket = serverSocket.accept();
				ClientHandler newClient = new ClientHandler(socket);
				System.out.println("ClientConnected");
				list.add(newClient);
				System.out.println(list.size());
				newClient.start();
			}
			try {
				serverSocket.close();
				for (int i = 0; i < list.size(); i++) {
					ClientHandler clientClose = list.get(i);
					try {
						clientClose.input.close();
						clientClose.output.close();
						clientClose.socket.close();
					} catch (IOException ioe) {
					}
				}
			} catch (Exception e) {
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
		private String message;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				username = (String) input.readObject();
				System.out.println("Tagit emot ett username från ny client: "+username);
				connectedUsers.add(username);
				System.out.println("Lagt till "+username+ " till connectedUsers");

				for (int i = 0; i < connectedUsers.size(); i++) {
					// output.writeObject("user,"+connectedUsers.get(i));
					newUser("user," + connectedUsers.get(i));
				}

				String status = (String) input.readObject();
				if (status.equals("rdy")) {
					for (int i = 0; i < messageList.size(); i++) {
						writeMessage(messageList.get(i));
						System.out.println(messageList.get(i));
					}
				}
				System.out.println(username + " connected");
			} catch (IOException | ClassNotFoundException e) {
			}
		}

		public void run() {
			while (true) {
				try {
					if (!connectedUsers.isEmpty()) {
						for (int i = 0; i < connectedUsers.size(); i++) {
							updateUsers("user," + connectedUsers.get(i));
							System.out.println("hej abdullah");
						}
					}

					message = (String) input.readObject();
					System.out.println(message);
					messageList.add(username + " - " + message);
					sendMessage(username + " - " + message);

				} catch (IOException | ClassNotFoundException e) {
					break;
				}
			}
		}

		private synchronized boolean writeMessage(Object obj) throws IOException {
			if (!socket.isConnected()) {
				socket.close();
				return false;
			}
			try {
				System.out.println(obj + "Hej");
				output.writeObject(obj);
			} catch (IOException e) {
			}
			return true;
		}

		private synchronized void updateUsers(Object obj) {
			try {
				output.writeObject(obj);
				output.flush();
			} catch (IOException e) {
				// e.printStackTrace();
			}
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
		new Server(1337, 100);
	}

}
