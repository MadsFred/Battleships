package gui.server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.server.ServerController;

public class ServerStartGui extends JFrame implements ActionListener{
	
	private ServerController serverController;
	private JPanel mainPanel = new JPanel();
	private JLabel ipLabel = new JLabel("Server ip: ");
	private JLabel nameLabel = new JLabel("Username: ");
	private JLabel ipField = new JLabel();
	private JTextField nameField = new JTextField();
	
	private JButton btnOk = new JButton("Start");
	private JButton btnCancel = new JButton("Cancel");
	
	public ServerStartGui(ServerController serverController){
		this.serverController = serverController;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(mainPanel);
		mainPanel.setLayout(new GridLayout(3,2));

		mainPanel.add(ipLabel);
		mainPanel.add(ipField);

		mainPanel.add(nameLabel);
		mainPanel.add(nameField);

		mainPanel.add(btnOk);
		mainPanel.add(btnCancel);
		
		try {
			ipField.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			System.err.println("Could not find ip address.");
		}
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);

		setVisible(true);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk){
			serverController.newMainGui();
			serverController.newSession(nameField.getText());
			setVisible(false);
		}
	}
}
