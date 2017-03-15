package gu;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
/**
 * Server that handles all connections
 * @author Lucas, David, Alexander, Elias, Filip, John
 *
 */
public class Server implements Runnable {

	private ServerSocket serverSocket;
	//logger
	private Logger log;
	private FileHandler fileHandler;
	private Thread server = new Thread(this);
	private boolean on = true;
	private ArrayList<ClientHandler> list = new ArrayList<ClientHandler>();
	private LinkedList<Message> messageList = new LinkedList<Message>();
	/**
	 * Constructor that starts logging then makes a new ServerSocket and starts server
	 * @param port port to open server on
	 * @throws IOException
	 */
	public Server(int port) throws IOException {
		startLog();
		serverSocket = new ServerSocket(port);
		server.start();

	}
	/**
	 * Method which send messages to all clients in list 
	 * @param obj Message to send
	 */
	public synchronized void sendMessage(Message obj) {
		for(int i = 0; i < list.size(); i++) {
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
	/**
	 * Method which removes a user from list
	 * @param username username to remove
	 * @throws IOException
	 */
	public void removeUser(String username) throws IOException {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).username.equals(username)) {
				list.remove(i);
			}
			newUser();
		}
	}

	/**
	 * method which add a new user to list
	 * @throws IOException
	 */
	public synchronized void newUser() throws IOException {
		String[] usernames= new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			usernames[i] = list.get(i).username;
		}
		Message message = new Message(usernames);
		sendMessage(message);
	}
	/**
	 * Waits for new connection and then sets it up when there is one
	 */
	public void run() {
		logHandler("New Session");
		try {
			while (on) {
				Socket socket = serverSocket.accept();
				ClientHandler newClient = new ClientHandler(socket);
				System.out.println("Server up");
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
	/**
	 * inner class that handles a single connection each
	 * @author Lucas, David, John, Filip, Alexander, Elias
	 *
	 */
	private class ClientHandler extends Thread {
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private int id;
		private String username;
//		private String message;
		private Message message;
		/**
		 * sets up connection and sets up the instance
		 * @param socket what socket to use
		 */
		public ClientHandler(Socket socket) {
			this.socket = socket;
			try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				username = (String) input.readObject();
				System.out.println("Tagit emot ett username från ny client: "+username);
				System.out.println("Lagt till "+username+ " till connectedUsers");
				for (int i = 0; i < messageList.size(); i++) {
					writeMessage(messageList.get(i));
					System.out.println(messageList.get(i));
				}
				System.out.println(username + " connected");
				logHandler(username + " has connected");
			} catch (IOException | ClassNotFoundException e) {
			}
		}
		/**
		 * Waits for a message and then handles it when recieved
		 */
		public void run() {
			while (true) {
				try {
					newUser();
					Date date = new Date();
					message = (Message)input.readObject();
					message.setServerRecieved(date.toString());
					if(message.getType().equals("image")) {
						logHandler(username + " has sent " + message.getImage());
					} else 
					{
						logHandler(username + " has written " + message.getMessage());
					}
	
					messageList.add(message);
					sendMessage(message);
				} catch (IOException e) {
					close();
					System.out.println(username + " kopplade ner");
					logHandler(username + " has logged out");
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
		/**
		 * Sends message to client.
		 * @param obj Message to send
		 * @return False if client is disconnected true if connected
		 * @throws IOException
		 */
		private synchronized boolean writeMessage(Message obj) throws IOException {
			if(!socket.isConnected()) {
				socket.close();
				return false;
			}
			try {
				System.out.println(obj + "Hej");
				output.writeObject(obj);
				Message message = (Message) obj;
				System.out.println(message.getUsernameList() + " i send till " + username);

				output.flush();
			} catch (IOException e) {

			}
			return true;
		}
		/**
		 * closes connection safely
		 */
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
	/**
	 * Start logger
	 */
	private void startLog() {
		try{
			log = Logger.getLogger("New Log");
			fileHandler = new FileHandler("C:/Users/Lucas/workspace/Gruppu2/log.txt");
			log.addHandler(fileHandler);
		} catch ( Exception e) {}
	}
	/**
	 * log messages
	 * @param msg msg to log
	 */
	public void logHandler(String msg) {
		log.info(msg + "\n");
	}

	public static void main(String[] args) throws IOException {

		new Server(1337);

	}

}
