package gu;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Message implements Serializable{
	private String[] recipients;
	private ArrayList<String> usernameList;
	private String sender;
	private ImageIcon image = null;
	private String message;
	private String type;
	
	
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
	
	public Message(ArrayList<String> usernameList) {
		this.usernameList = usernameList;
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
	
	public ArrayList getUsernameList() {
		return usernameList;
	}
	
	

}
