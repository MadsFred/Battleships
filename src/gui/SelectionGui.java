package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import app.client.ClientController;
import app.server.ServerController;
	
public class SelectionGui extends JFrame implements ActionListener {
	private JPanel radioPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JRadioButton btnClient = new JRadioButton("Client");
	private JRadioButton btnServer = new JRadioButton("Server");
	
	private JButton btnOk = new JButton("Okay");
	
	public SelectionGui(){
		setLayout(new BorderLayout());
		
		add(radioPanel,BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		radioPanel.setLayout(new BoxLayout(radioPanel,BoxLayout.PAGE_AXIS));
		radioPanel.add(btnClient);
		radioPanel.add(btnServer);
		
		buttonPanel.add(btnOk);
		
		btnOk.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk){
			if(btnClient.isSelected()){
				ClientController clientController = new ClientController();
				clientController.newStartGui();
				setVisible(false);
			}
			if(btnServer.isSelected()){
				ServerController serverController = new ServerController();
				serverController.newStartGui();
				setVisible(false);
			}
		}
	}
}
