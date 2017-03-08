package gu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Viewer extends JPanel implements ActionListener{
	private ClientController controller;
	private JTextField messageInput = new JTextField();
	private JTextArea messageArea = new JTextArea();
	private JTextArea messageUsers = new JTextArea ("AKTIVA ANVÄNDARE");
	private JButton btnSend = new JButton("Skicka");
	private JPanel panelSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelWest = new JPanel ();
	
	
	public Viewer(ClientController controller){
		this.controller = controller;
		setLayout(new BorderLayout());
		messageArea.setPreferredSize(new Dimension(500,500));
		messageInput.setPreferredSize(new Dimension(450,100));
		messageUsers.setPreferredSize(new Dimension(150,500));
		btnSend.setPreferredSize(new Dimension(75,50));
		panelSouth.add(messageInput, BorderLayout.CENTER);
		panelSouth.add(btnSend, BorderLayout.EAST);
		panelCenter.add(new JScrollPane(messageArea), BorderLayout.CENTER);

		panelCenter.add(messageArea, BorderLayout.CENTER);
		panelWest.add(messageUsers, BorderLayout.CENTER);

		add(panelCenter,BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		add(panelWest, BorderLayout.WEST);
		messageInput.addActionListener(this);
		btnSend.addActionListener(this);
		messageArea.setEditable(false);
		messageUsers.setEditable(false);

	}
	
	public void updateChat(Object o){
		if(o instanceof String){
			messageArea.append("\n" + (String)o);
		}
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Du klickade på enter/skicka");
		controller.sendObject((Object)messageInput.getText());
		messageInput.setText("");
	}
}
