package gu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GUI extends JPanel implements ActionListener{
	private Controller controller;
	private JTextField messageInput = new JTextField();
	private JTextArea messageArea = new JTextArea();
	private JButton btnSend = new JButton("Skicka");
	private JPanel panelSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	
	public GUI(Controller controller){
		this.controller = controller;
		setLayout(new BorderLayout());
		messageArea.setPreferredSize(new Dimension(500,500));
		messageInput.setPreferredSize(new Dimension(200,50));
		btnSend.setPreferredSize(new Dimension(75,50));
		panelSouth.add(messageInput, BorderLayout.CENTER);
		panelSouth.add(btnSend, BorderLayout.EAST);
		panelCenter.add(messageArea, BorderLayout.CENTER);
		add(panelCenter,BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		messageInput.addActionListener(this);
		btnSend.addActionListener(this);
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
