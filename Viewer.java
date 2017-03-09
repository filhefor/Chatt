package gu;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.FileHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Viewer extends JPanel implements ActionListener, KeyListener{
	
	private ClientController controller;
	
	private JTextField messageInput = new JTextField();
	private JTextArea messageArea = new JTextArea();	
	private JTextArea connectedUsers= new JTextArea();

	private JButton sendButton = new JButton("Skicka");
	private JButton imageButton = new JButton("Lägg till bild");
	
	private JPanel btnPanel = new JPanel();
	private JPanel panelSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelWest = new JPanel ();
	private JScrollPane scrollPane = new JScrollPane(messageArea);
	
	private JFileChooser fileChooser = new JFileChooser();
	private FileFilter imageFilter = new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes());
	
	
	public Viewer(ClientController controller){
		this.controller = controller;
		setPreferredSize(new Dimension(700,600));
		setLayout(new BorderLayout());
		
		sendButton.setPreferredSize(new Dimension(130,35));
		imageButton.setPreferredSize(new Dimension(130,35));
		btnPanel.setPreferredSize(new Dimension(170,100));
		messageInput.setPreferredSize(new Dimension(515, 100));
		scrollPane.setPreferredSize(new Dimension(510,480));
		connectedUsers.setPreferredSize(new Dimension(150,480));
		
		connectedUsers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		messageInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		btnPanel.add(imageButton, BorderLayout.NORTH);
		btnPanel.add(sendButton, BorderLayout.SOUTH);
		panelWest.add(connectedUsers, BorderLayout.CENTER);
		panelCenter.add(scrollPane, BorderLayout.CENTER);
		panelSouth.add(messageInput, BorderLayout.CENTER);
		panelSouth.add(btnPanel, BorderLayout.EAST);
		
		messageInput.addKeyListener(this);
		sendButton.addActionListener(this);
		messageArea.setEditable(false);
		connectedUsers.setEditable(false);
		imageButton.addActionListener(this);		
		
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
		String users = connectedUsers.getText();
		if(!users.contains(user)){
			connectedUsers.setText(connectedUsers.getText()+"\n"+user);
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {
			System.out.println("Du klickade på enter/skicka");
			controller.sendObject((Object)messageInput.getText());
			messageInput.setText("");
		}
		if(e.getSource() == imageButton) {
			fileChooser();
		}
		
	}
	
	public void keyPressed(KeyEvent e){
	    if(e.getKeyCode() == KeyEvent.VK_ENTER){
	    e.consume();
	    sendButton.doClick();
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
	
	public void fileChooser() {
		String image;
		
		fileChooser.setCurrentDirectory(new java.io.File("C:/Users"));
		fileChooser.setDialogTitle("Välj bild");
		fileChooser.setFileFilter(imageFilter);
		
		if(fileChooser.showOpenDialog(imageButton) == JFileChooser.APPROVE_OPTION) {
//			
		}
		image = fileChooser.getSelectedFile().getAbsolutePath();
		controller.sendImage(image);
		System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
		
	}
}
