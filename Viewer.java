package gu;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Viewer extends JPanel implements ActionListener, KeyListener{
	
	private ClientController controller;
	private JTextField messageInput = new JTextField();
	private StyledDocument doc = new DefaultStyledDocument();
	private Style imgStyle = doc.addStyle("imgStyle", null);;
	private JTextPane messageArea = new JTextPane(doc);	
	private JTextArea connectedUsers= new JTextArea("Aktiva användare: \n");
	private JButton sendButton = new JButton("Skicka");
	private JButton imageButton = new JButton("Lägg till bild");
	
	private JPanel btnPanel = new JPanel();
	private JPanel panelSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelWest = new JPanel ();
	private JScrollPane scrollPane = new JScrollPane(messageArea);
	private JFileChooser fc = new JFileChooser();

	
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
		imageButton.addActionListener(this);
		messageArea.addKeyListener(this);
    
		messageArea.setEditable(false);
		connectedUsers.setEditable(false);
		messageArea.setAutoscrolls(true);
		messageArea.setCaretPosition(doc.getLength());
    
		add(panelWest, BorderLayout.WEST);
		add(panelSouth, BorderLayout.SOUTH);
		add(panelCenter, BorderLayout.CENTER);
		

	}
	
	public void updateChatText(Message o){
		try{
		if(o.getRecipients().length <= 0 || o.getSender().equals(controller.getUsername())){
//			messageArea.append(o.getSender() + ": " + o.getMessage() + "\n");
			doc.insertString(doc.getLength(), o.getSender() + ": " +o.getMessage() + "\n", null);
		}else{
			String[] arr = o.getRecipients();
			for(int i = 0; i < arr.length; i++){
				if(arr[i].equals(controller.getUsername())){
					doc.insertString(doc.getLength(), o.getMessage(), null);
				}
			}
		}
		}catch(BadLocationException e) {}
		
	}
	
	public void updateChatImage(Message o) {
		if(o.getRecipients().length <= 0 || o.getSender().equals(controller.getUsername())){
			Image image = o.getImage().getImage().getScaledInstance(320, 210,
					java.awt.Image.SCALE_SMOOTH);
			JLabel label = new JLabel(new ImageIcon(image));
			StyleConstants.setComponent(imgStyle, label);
			try{
				doc.insertString(doc.getLength(), "ignored text", imgStyle);
				doc.insertString(doc.getLength(), "\n", null);
			} catch(BadLocationException e) {
				e.printStackTrace();
			}
			
		}else{
			String[] arr = o.getRecipients();
			for(int i = 0; i < arr.length; i++){
				if(arr[i].equals(controller.getUsername())){
					Image image = o.getImage().getImage().getScaledInstance(320, 210,
							java.awt.Image.SCALE_SMOOTH);
					ImageIcon newImage = new ImageIcon(image);
					JLabel label = new JLabel(newImage);
					StyleConstants.setIcon(imgStyle, newImage);
					try{
						System.out.println("bildskrivssut");
					doc.insertString(doc.getLength(), "ignored text", imgStyle);
					doc.insertString(doc.getLength(), "\n", null);
					System.out.println("bildskrivssut");
					doc.insertString(doc.getLength(), "\n", null);
					} catch(BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	
	public void updateUsers(String[] strings){
		connectedUsers.setText("Aktiva användare:\n");
		for(int i = 0; i < strings.length; i++) {
		System.out.println(strings[i]);
		}
		for(int i = 0; i < strings.length; i++){
			connectedUsers.append(strings[i]+"\n");
		}
		
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {
			System.out.println("Du klickade på enter/skicka");
//			controller.sendObject((Object)messageInput.getText());
			String[] recipients = {};
			controller.createTextMessage(recipients, messageInput.getText());
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
		ImageIcon image;
		
		fileChooser.setCurrentDirectory(new java.io.File("C:/Users"));
		fileChooser.setDialogTitle("Välj bild");
		fileChooser.setFileFilter(imageFilter);
		
		if(fileChooser.showOpenDialog(imageButton) == JFileChooser.APPROVE_OPTION) {
//			
		}
		String[] arr = {};
		
		image = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
		controller.createImageMessage(arr,image);
		System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
		
	}
}
