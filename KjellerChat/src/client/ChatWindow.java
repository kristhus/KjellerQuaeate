package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class ChatWindow extends JFrame {
	
	private JTextArea messageArea;
	
	private Client client;
	private CardLayout layout;
	
	private CustomJTextField username;
	private CustomJTextField pw;
	
	
	public ChatWindow(Client client) {
		this.client = client;
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {}
		}

		chooseRoom();
		
		setPreferredSize(new Dimension(617,840));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public void chooseRoom() {
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(250,250,250));
		username = new CustomJTextField(new JTextField(), "Username");
		username.setToolTipText("Choose a username. It cannot contain non-vulgar words");
		pw = new CustomJTextField(new JTextField(), "Password");
		JButton login = new JButton("Login");
		login.setActionCommand("login");
		login.addActionListener(new ActionMan());
		
		SpringLayout spl = new SpringLayout();
		loginPanel.setLayout(spl);
		
		//USERNAME
		loginPanel.add(username);
		username.setPreferredSize(new Dimension(300,50));
		spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, username, 0, SpringLayout.HORIZONTAL_CENTER, loginPanel);
		spl.putConstraint(SpringLayout.NORTH, username, 100, SpringLayout.NORTH, loginPanel);
		username.setActionCommand("loginField");
		username.addKeyListener(new LoginStroke());
		
		//PASSWORD
		loginPanel.add(pw);
		pw.setPreferredSize(new Dimension(300,50));
		spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, pw, 0, SpringLayout.HORIZONTAL_CENTER, loginPanel);
		spl.putConstraint(SpringLayout.NORTH, pw, 20, SpringLayout.SOUTH, username);
		pw.setActionCommand("loginField");
		pw.addKeyListener(new LoginStroke());
		
		//KNAPPERUD
		loginPanel.add(login);
		login.setPreferredSize(new Dimension(100,50));
		spl.putConstraint(SpringLayout.HORIZONTAL_CENTER, login, 0, SpringLayout.HORIZONTAL_CENTER, loginPanel);
		spl.putConstraint(SpringLayout.NORTH, login, 50, SpringLayout.SOUTH, pw);
		pw.setBackground(Color.white);
		add(loginPanel);
	}
	
	
	public void connected() {
//		removeAll();
		JPanel chatPanel = new JPanel();
		SpringLayout spl = new SpringLayout();
		chatPanel.setLayout(spl);
		chatPanel.setBackground(Color.white);
		
		JScrollPane chat = new JScrollPane();
		JScrollPane connectedUsers = new JScrollPane();
		JButton sendButton = new JButton("Send");
		messageArea = new JTextArea();
		Font messageFont = new Font(Font.SERIF, Font.CENTER_BASELINE, 16);
		messageArea.setFont(messageFont);
		messageArea.addKeyListener(new MessageKeystrokes());
		
		//Where the chat appears
		chatPanel.add(chat);
		chat.setBackground(Color.white);
		chat.setPreferredSize(new Dimension(400,600));
		spl.putConstraint(SpringLayout.WEST, chat, 0, SpringLayout.WEST, chatPanel);
		spl.putConstraint(SpringLayout.NORTH, chat, 0, SpringLayout.NORTH, chatPanel);
		
		//(SpringLayout.WEST, this, locationStartX, SpringLayout.WEST, MainFrame.getCalendarView());
		
		// The panel with connected users
		chatPanel.add(connectedUsers);
		connectedUsers.setBackground(Color.red);
		connectedUsers.setPreferredSize(new Dimension(200,600));
		spl.putConstraint(SpringLayout.WEST, connectedUsers, 0, SpringLayout.EAST, chat);
		spl.putConstraint(SpringLayout.NORTH, connectedUsers, 0, SpringLayout.NORTH, chatPanel);
		
		// The input field for the user
		chatPanel.add(messageArea);
//		messageArea.setBackground(Color.red);
		messageArea.setPreferredSize(new Dimension(400,200));
		spl.putConstraint(SpringLayout.NORTH, messageArea, 0, SpringLayout.SOUTH, chat);
		
		
		chatPanel.add(sendButton);
		sendButton.setPreferredSize(new Dimension(200,200));

//		sendButton.setBackground(Color.blue);
		spl.putConstraint(SpringLayout.WEST, sendButton, 0, SpringLayout.EAST, messageArea);
		spl.putConstraint(SpringLayout.NORTH, sendButton, 0, SpringLayout.SOUTH, connectedUsers);
		
		
		add(chatPanel);
//		layout.first(chatPanel);
	}
	
	public class LoginStroke implements KeyListener {

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
				System.out.println("LET'S GO");
				if(client.login(username.getText(), pw.getText()) || client.debug) {
					getContentPane().removeAll();
					connected();
					revalidate();
				}
				else
					errorDialouge("Feil ved innlogging");
			}
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class MessageKeystrokes implements KeyListener {

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				if(messageArea.getText() != null) {
					System.out.println("Text: " + messageArea.getText());
					client.sendMessage(messageArea.getText());
				}
				messageArea.setText("");
				break;
			}
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void errorDialouge(String error) {
		JOptionPane.showMessageDialog(this,
			    "There was an error with your login request.",
			    "Login error",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	
	public class ActionMan implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			switch(((JButton) arg0.getSource()).getActionCommand()) {
			
				case "login" :
					System.out.println("LET'S GO");
					if(client.login(username.getText(), pw.getText()) || client.debug) {
						getContentPane().removeAll();
						connected();
						revalidate();
					}
					else
						errorDialouge("Feil ved innlogging");
					break;
			
			}
		}
		
	}
	
}