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
/**
 * Viewer class handles GUI
 * @author Lucas, David, Alexander, FIlip, Elias, John
 */
public class Viewer extends JPanel implements ActionListener, KeyListener{
	private ClientController controller;
	private JTextField messageInput = new JTextField();
	private JTextField recipientsInput = new JTextField();
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
	
	/**
	 * Constructor adds to panels and add panels
	 * @param controller ClientController to set
	 */
	public Viewer(ClientController controller){
		this.controller = controller;
		setPreferredSize(new Dimension(700,600));
		setLayout(new BorderLayout());
		
		sendButton.setPreferredSize(new Dimension(130,35));
		imageButton.setPreferredSize(new Dimension(130,35));

		btnPanel.setPreferredSize(new Dimension(170,100));
		messageInput.setPreferredSize(new Dimension(400, 100));
		recipientsInput.setPreferredSize(new Dimension(75,100));
		scrollPane.setPreferredSize(new Dimension(510,480));
		connectedUsers.setPreferredSize(new Dimension(150,480));
		
		connectedUsers.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		recipientsInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		messageInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		btnPanel.add(imageButton, BorderLayout.NORTH);
		btnPanel.add(sendButton, BorderLayout.SOUTH);
		panelWest.add(connectedUsers, BorderLayout.CENTER);
		panelCenter.add(scrollPane, BorderLayout.CENTER);
		panelSouth.add(recipientsInput, BorderLayout.WEST);
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
	/**
	 * updates JTextPane with text
	 * @param username username to add to message
	 * @param message message to add to JTextPane
	 */
	public void updateChatText(String username, String message){
		try{
			doc.insertString(doc.getLength(), username + ": " + message + "\n", null);
		}catch(BadLocationException e) {}
		
	}
	/**
	 * Updates JTextPane with image
	 * @param username username to update
	 * @param imageLbl image to update
	 */
	public void updateChatImage(String username, JLabel imageLbl) {
		try{
			StyleConstants.setComponent(imgStyle, imageLbl);
			doc.insertString(doc.getLength(), username, null);
			doc.insertString(doc.getLength(), "ignored text", imgStyle);
			doc.insertString(doc.getLength(), "\n", null);
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates users connected
	 * @param strings users to add
	 */
	public void updateUsers(String[] strings){
		connectedUsers.setText("Aktiva användare:\n");
		for(int i = 0; i < strings.length; i++) {
		}
		for(int i = 0; i < strings.length; i++){
			connectedUsers.append(strings[i]+"\n");
		}
		
	}

	/**
	 * creates a Message with messageInput and recipients.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendButton) {
			String recipients = recipientsInput.getText();
			String[] recipientsarr = recipientsarr = recipients.split(",");
			if(recipientsarr[0].equals("")) {
				recipientsarr = new String[0];
			}
			controller.createTextMessage(recipientsarr, messageInput.getText());
			messageInput.setText("");
		}
		if(e.getSource() == imageButton) {
			fileChooser();
		}
	}
	/**
	 * if Enter is pressed click sendbutton
	 */
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
	/**
	 * Filechooser makes a Message with type image and sends to recipients
	 */
	public void fileChooser() {
		ImageIcon image;
		
		fileChooser.setCurrentDirectory(new java.io.File("C:/Users"));
		fileChooser.setDialogTitle("Välj bild");
		fileChooser.setFileFilter(imageFilter);
		
		if(fileChooser.showOpenDialog(imageButton) == JFileChooser.APPROVE_OPTION) {		
		}
		String recipients = recipientsInput.getText();
		String[] recipientsarr = recipients.split(",");
		if(recipientsarr[0].equals("")) {
			recipientsarr = new String[0];
		}
		image = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
		controller.createImageMessage(recipientsarr,image);
		System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
		
	}
}
