package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import app.interfaces.AppController;
import data.ShipType;

public class MainGui extends JFrame implements ActionListener {
	
	private static final int GUI_WIDTH = 900;
	private static final int GUI_HEIGHT = 700;
	
	private AppController appController;
	
	private JPanel chatPanel = new JPanel();
	private JPanel gamePanel = new JPanel();
	
	// header
	private JLabel headerLabel = new JLabel("Battleships");
	
	// chat panel
	private JScrollPane chatScroll = null;
	private JTextArea chatArea = new JTextArea(25,18);
	
	// game panel
	private GridPanel playerPanel = new GridPanel();
	private GridPanel opponentPanel = new GridPanel();
	
	private JPanel messagePanel = new JPanel();
	private JTextField chatField = new JTextField(18);
	private JButton btnSend = new JButton("Send");
	
	private JLabel statusLabel = new JLabel("Current Ship: ");
	private JLabel shipsLeftLabel = new JLabel();
	private JButton btnAction = new JButton("Ship Ready!");
	
	// game Panel
	
	
	public MainGui(AppController appController){
		this.appController = appController;
		
		setTitle("Battleships");
		setLayout(new BorderLayout());
		// add panels
		add(headerLabel,BorderLayout.NORTH);
		add(chatPanel,BorderLayout.EAST);
		add(gamePanel,BorderLayout.CENTER);
		
		// header label
		headerLabel.setFont(new Font("verdana", Font.BOLD, 18));
		
		// add borders to panels
		chatPanel.setBorder(BorderFactory.createTitledBorder("Chat"));
		gamePanel.setBorder(BorderFactory.createTitledBorder("Game"));
		playerPanel.setBorder(BorderFactory.createTitledBorder("Your ships"));
		opponentPanel.setBorder(BorderFactory.createTitledBorder("Opponents ships"));
		
		// chat panel
		chatPanel.setLayout(new BoxLayout(chatPanel,BoxLayout.PAGE_AXIS));
		chatScroll = new JScrollPane(chatArea);
		chatPanel.add(chatScroll);
		chatPanel.add(messagePanel);
		messagePanel.add(chatField);
		messagePanel.add(btnSend);
		chatPanel.add(statusLabel);
		chatPanel.add(shipsLeftLabel);
		chatPanel.add(btnAction);
		
		chatScroll.setViewportView(chatArea);
		
		chatArea.setEditable(false);
		
		// game panel
		gamePanel.setLayout(new GridLayout(2,0));
		gamePanel.add(playerPanel);
		gamePanel.add(opponentPanel);
		
		
		// action listeners
		btnSend.addActionListener(this);
		chatField.addActionListener(this);
		btnAction.addActionListener(this);
		
		// final stuff
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(GUI_WIDTH,GUI_HEIGHT);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Chat
		if(e.getSource() == btnSend || e.getSource() == chatField){
			appController.getSocketThread().sendChatMessage(appController.getName() + ": " + chatField.getText()); // send message to server
			writeMessage(appController.getName() + ": " + chatField.getText()); // write message locally
			chatField.setText(""); // clear chat field
		}	
		// Ship placed! If not correctly placed, error message, and start over. 
		// if placed legally, add to grid (both gui and data) and go to next.
		if(e.getSource() == btnAction && appController.isPlacing() && !appController.allShipsPlaced()){
			if(playerPanel.placementLegal(appController.getPlacementController().getCurrentShip()) ){
				// places ships on data grid
				appController.getGrid().placeShip(playerPanel.getSelected(), appController.getPlacementController().getCurrentShip().getName()); 
				// places ships on gui grid & deselect cells
				playerPanel.placeShip(playerPanel.getSelected());
				
				appController.getPlacementController().nextShip(); // proceed to next ship to be placed
			}
			else{
				playerPanel.deselectAll();
			}
		}
		if(e.getSource() == btnAction && !appController.isPlacing()){
			if(!appController.getGameController().hasTurn()) JOptionPane.showMessageDialog(this, "It is not your turn."); // Not your turn.
			if(!appController.isOpponentReady()) JOptionPane.showMessageDialog(this, "Opponent is not ready yet."); // opponent not ready
			else if(opponentPanel.getSelected().length == 1 && appController.getGameController().hasTurn() && appController.isOpponentReady()){
				appController.getSocketThread().shoot(opponentPanel.getSelected()[0].getColumn(),opponentPanel.getSelected()[0].getRow());
				opponentPanel.deselectAll();
			}
		}
	}
	public void writeMessage(String line) {
		chatArea.append(line + "\n");
	}
	public GridPanel getPlayerGrid(){
		return playerPanel;
	}
	public GridPanel getOpponentGrid(){
		return opponentPanel;
	}
	public void setShipLabel(ShipType shipType) {
		if(shipType == null){
			statusLabel.setText("");
		}
		else{
			statusLabel.setText("Current Ship: " + shipType.getName() +", size: " + shipType.getSize());
		}
	}
	public void setTurnLabel(boolean b){
		if(b==false) statusLabel.setText("Other players turn.");
		else statusLabel.setText("Your turn.");
	}
	public void setShipsLeftLabel(int ships){
		if(ships == -1){
			shipsLeftLabel.setText("");
		}
		else{
			shipsLeftLabel.setText(ships + " ships of this type left.");
		}
	}
	public void setGameGui(){
		btnAction.setText("Shoot!");
	}
}
