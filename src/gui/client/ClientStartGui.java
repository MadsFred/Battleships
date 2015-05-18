package gui.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.client.ClientController;

public class ClientStartGui extends JFrame implements ActionListener {

	private ClientController clientController;

	private JPanel mainPanel = new JPanel();
	private JLabel ipLabel = new JLabel("Server ip: ");
	private JLabel nameLabel = new JLabel("Username: ");
	private JTextField ipField = new JTextField();
	private JTextField nameField = new JTextField();

	private JButton btnOk = new JButton("Connect");
	private JButton btnCancel = new JButton("Cancel");

	public ClientStartGui(ClientController clientController){

		this.clientController = clientController;
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(mainPanel);
		mainPanel.setLayout(new GridLayout(3,2));

		mainPanel.add(ipLabel);
		mainPanel.add(ipField);

		mainPanel.add(nameLabel);
		mainPanel.add(nameField);

		mainPanel.add(btnOk);
		mainPanel.add(btnCancel);

		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);

		setVisible(true);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk){
			clientController.newMainGui();
			clientController.newSession(ipField.getText(),nameField.getText());
			setVisible(false);
		}
	}
}
