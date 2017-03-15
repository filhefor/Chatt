package gu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;

public class Message implements Serializable{
	private String[] recipients;
	private String[] usernameList;
	private String sender;
	private ImageIcon image = null;
	private String message;
	private String type;

	private String serverRecieved;
	private String clientRecieved;

	
	
	public Message(String sender, String[] recipients, ImageIcon image){
		this.sender=sender;
		this.recipients=recipients;
		this.image=image;
		this.type = "image";
	}
	public Message(String sender, String[] recipients, String message) {
		this.sender = sender;
		this.recipients = recipients;
		this.message = message;
		this.type = "message";
	}
	
	public Message(String[] usernameList) {
		this.usernameList = usernameList;
		this.recipients = new String[0];
		this.type = "usernameList";
	}
	
	public String[] getRecipients() {
		return recipients;
	}
	
	public String getType() {
		return type;
	}
	
	public String getSender() {
		return sender;
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String[] getUsernameList() {
		return usernameList;
	}
	public void setServerRecieved(String string){
		this.serverRecieved=string;
	}
	public String getServerRecieved(){
		return serverRecieved;
	}
	public void setClientRecieved(String time){
		this.clientRecieved=time;
	}
	public String getClientRecieved(){
		return clientRecieved;
	}
	
	

}
