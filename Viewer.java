package gu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

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
		setPreferredSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		
		messageArea.setPreferredSize(new Dimension(500,300));
		messageInput.setPreferredSize(new Dimension(450,100));
		messageUsers.setPreferredSize(new Dimension(150,500));
		btnSend.setPreferredSize(new Dimension(75,50));
		
		panelSouth.add(messageInput);
		panelSouth.add(btnSend);
		panelCenter.add(new JScrollPane(messageArea));
//		panelCenter.add(messageArea, BorderLayout.CENTER);
		panelWest.add(messageUsers);

		add(panelCenter);
		add(messageUsers);
		add(panelSouth);
		
		messageArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		messageUsers.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
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
