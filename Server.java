package gu;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.util.LinkedList;

public class Server {
	
	private ServerSocket server;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private Object objectToSend;

	private Thread serverThread = new ClientHandler();

	
	public Server(int port) throws IOException {
		server= new ServerSocket(port, 100);
		startRunning();
		
	}
	public void startRunning(){
		try{
			while(true){
				try{
					connection = server.accept();
					setupStreams();
				}catch(EOFException eofException){
					eofException.printStackTrace();
					System.out.println("EOFException in startRunning method");
				}
			}
		}catch (IOException ioException){
			ioException.printStackTrace();
			System.out.println("IOException in startRunning method");
		}
	}
	public void setupStreams() throws IOException{
		System.out.println("Server running, waiting for connection...");
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());

		System.out.println("Streams connected");

	}


	private class ClientHandler extends Thread{

		
		public void run() {
			Object outputObject, inputObject;
			 while(true){
				 try{
					 inputObject = input.readObject();
					 //System.out.println(inputObject.toString());
					 output.writeObject("SERVER- "+inputObject);
					 output.flush();
				 }catch(IOException | ClassNotFoundException e){
					 System.out.println(e.getMessage());
				 }
			 }
			
		}
		
	}

//	private class ConnectionThread extends Thread {
//
//		public void run() {
//
//			while (true) {
//				try (Socket socket = serverSocket.accept();
//						ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
//						ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
//					inputObject = input.readObject();
//
//					output.writeObject(inputObject);
//					output.flush();
//				} catch (IOException | ClassNotFoundException e) {
//				}
//			}
//
//		}
//	}

	public static void main(String[] args) throws IOException {
		new Server(1337);
	}

}
