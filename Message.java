package gu;

import javax.swing.ImageIcon;

public class Message {
	private String[] recipients;
	private String recipient;
	private String sender;
	private ImageIcon image = null;
	private String message;
	
	public Message(String sender, String recipient, String message, ImageIcon image){
		this.sender=sender;
		this.recipient=recipient;
		this.message=message;
		this.image=image;
	}
	public String[] getRecipients() {
		return recipients;
	}

	public String getRecipient() {
		return recipient;
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
	
	

}
