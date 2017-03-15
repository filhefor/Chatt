package gu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
/**
 * Message class which cointain all information regarding messages
 * @author Lucas, David, Elias, Alexander, Filip, John
 *
 */
public class Message implements Serializable{
	private String[] recipients;
	private String[] usernameList;
	private String sender;
	private ImageIcon image = null;
	private String message;
	private String type;
	private String serverRecieved;
	private String clientRecieved;

	/**
	 * Constructor which makes Message of type image
	 * @param sender Sender
	 * @param recipients Recipients
	 * @param image Image to send
	 */
	public Message(String sender, String[] recipients, ImageIcon image){
		this.sender=sender;
		this.recipients=recipients;
		this.image=image;
		this.type = "image";
	}
	/**
	 * constructor which makes Message of type message
	 * @param sender sender
	 * @param recipients recipients
	 * @param message message to send
	 */
	public Message(String sender, String[] recipients, String message) {
		this.sender = sender;
		this.recipients = recipients;
		this.message = message;
		this.type = "message";
	}
	/**
	 * Message of type usernameList 
	 * @param usernameList usernameList
	 */
	public Message(String[] usernameList) {
		this.usernameList = usernameList;
		this.recipients = new String[0];
		this.type = "usernameList";
	}
	/**
	 * get recipients
	 * @return recipients
	 */
	public String[] getRecipients() {
		return recipients;
	}
	/**
	 * get type
	 * @return type
	 */
	public String getType() {
		return type;
	}
	/**
	 * Get sender
	 * @return sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * get Image
	 * @return image
	 */
	public ImageIcon getImage() {
		return image;
	}
	/**
	 * getMessage
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * get usernameList
	 * @return usernameList
	 */
	public String[] getUsernameList() {
		return usernameList;
	}
	/**
	 * sets time when server recieve message
	 * @param string time
	 */
	public void setServerRecieved(String string){
		this.serverRecieved=string;
	}
	/**
	 * get time when server recieved message
	 * @return time
	 */
	public String getServerRecieved(){
		return serverRecieved;
	}
	/**
	 * set time when client recieve message
	 * @param time
	 */
	public void setClientRecieved(String time){
		this.clientRecieved=time;
	}
	/**
	 * get time when client recieve message
	 * @return time
	 */
	public String getClientRecieved(){
		return clientRecieved;
	}
}