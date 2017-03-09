package gu;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;

import javax.swing.*;

public class Viewer extends JPanel implements ActionListener, KeyListener{
	
	private ClientController controller;
	private JTextField messageInput = new JTextField();
	private JTextArea messageArea = new JTextArea();	
	private JTextArea connectedUsers= new JTextArea();

	private JButton btnSend = new JButton("Skicka");

	private JButton getImagebtn = new JButton("Lägg till bild");
	
	private JPanel btnPanel = new JPanel();
	private JPanel panelSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelWest = new JPanel ();
	private JScrollPane scrollPane = new JScrollPane(messageArea);
	private JFileChooser fc = new JFileChooser();

	
	public Viewer(ClientController controller){
		this.controller = controller;
		setPreferredSize(new Dimension(700,600));
		setLayout(new BorderLayout());
		

		btnSend.setPreferredSize(new Dimension(130,35));
		getImagebtn.setPreferredSize(new Dimension(130,35));
		btnPanel.setPreferredSize(new Dimension(170,100));
		messageInput.setPreferredSize(new Dimension(515, 100));
		scrollPane.setPreferredSize(new Dimension(510,480));
		connectedUsers.setPreferredSize(new Dimension(150,480));
		
		connectedUsers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		messageInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		btnPanel.add(getImagebtn, BorderLayout.NORTH);
		btnPanel.add(btnSend, BorderLayout.SOUTH);
		panelWest.add(connectedUsers, BorderLayout.CENTER);
		panelCenter.add(scrollPane, BorderLayout.CENTER);
		panelSouth.add(messageInput, BorderLayout.CENTER);
		panelSouth.add(btnPanel, BorderLayout.EAST);

		
		messageArea.addKeyListener(this);
		btnSend.addActionListener(this);
		messageArea.setEditable(false);

		connectedUsers.setEditable(false);
		getImagebtn.addActionListener(this);
		
    
		add(panelWest, BorderLayout.WEST);
		add(panelSouth, BorderLayout.SOUTH);
		add(panelCenter, BorderLayout.CENTER);
		

	}
	
	public void updateChat(Message o){
		if(o.getRecipients().length <= 0 || o.getSender().equals(controller.getUsername())){
			messageArea.append(o.getMessage());
		}else{
			String[] arr = o.getRecipients();
			for(int i = 0; i < arr.length; i++){
				if(arr[i].equals(controller.getUsername())){
					messageArea.append(o.getMessage());
				}
			}
		}
		
	}

	
	public void updateUsers(ArrayList<String> users){
		connectedUsers.setText("");
		for(int i = 0; i < users.size(); i++){
			connectedUsers.append(users.get(i)+"\n");
		}
		
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSend){
		System.out.println("Du klickade på enter/skicka");
		controller.sendObject((Object)messageInput.getText());
		messageInput.setText("");
		}
		if(e.getSource() == getImagebtn){
			controller.sendObject((Object) fc.showOpenDialog(null));
		}
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
