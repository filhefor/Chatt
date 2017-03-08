package gu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Viewer extends JPanel implements ActionListener, KeyListener{
	
	private ClientController controller;
	
	private JTextField messageInput = new JTextField();
	private JTextArea messageArea = new JTextArea();	
	private JTextArea connectedUsers= new JTextArea();

	private JButton btnSend = new JButton("Skicka");
	
	private JPanel panelSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelWest = new JPanel ();
	private JScrollPane scrollPane = new JScrollPane(messageArea);
	
	public Viewer(ClientController controller){
		this.controller = controller;
		setPreferredSize(new Dimension(700,600));
		setLayout(new BorderLayout());
		
		messageInput.setPreferredSize(new Dimension(450, 100));
		scrollPane.setPreferredSize(new Dimension(400,400));
		connectedUsers.setPreferredSize(new Dimension(150,450));
		connectedUsers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		panelWest.add(connectedUsers, BorderLayout.CENTER);
		panelCenter.add(scrollPane, BorderLayout.CENTER);
		panelSouth.add(messageInput, BorderLayout.CENTER);
		panelSouth.add(btnSend, BorderLayout.EAST);
		
		
		messageInput.addKeyListener(this);
		btnSend.addActionListener(this);
		messageArea.setEditable(false);
		connectedUsers.setEditable(false);
		
		add(panelWest, BorderLayout.WEST);
		add(panelSouth, BorderLayout.SOUTH);
		add(panelCenter, BorderLayout.CENTER);
		
	}
	
	public void updateChat(Object o){
		if(o instanceof String){
			messageArea.append("\n" + (String)o);
		}
	}
	
	public void updateUsers(String user){
		String users = messageUsers.getText();
		if(!users.contains(user)){
			messageUsers.setText(messageUsers.getText()+"\n"+user);
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Du klickade på enter/skicka");
		controller.sendObject((Object)messageInput.getText());
		messageInput.setText("");
	}
	
	public void keyPressed(KeyEvent e){
	    if(e.getKeyCode() == KeyEvent.VK_ENTER){
	    e.consume();
	    btnSend.doClick();
	    }
	
}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
