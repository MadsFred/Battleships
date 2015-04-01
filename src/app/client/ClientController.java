package app.client;

import gui.MainGui;
import gui.client.ClientStartGui;
import app.GameController;
import app.PlacementController;
import app.interfaces.AppController;
import app.interfaces.SocketThread;
import data.Grid;

public class ClientController implements AppController{
	
	private boolean opponentReady = false;
	private PlacementController placementController = null;
	private GameController gameController = null;
	private Grid playerGrid;
	private boolean isPlacing = true;
	private boolean allShipsPlaced = false;
	private ClientSocketThread socketThread = null;
	private String userName = null;
	private MainGui gui;

	public void newStartGui() {
		 new ClientStartGui(this);
	}

	public void newSession(String ip, String name) {
		this.userName = name;
		
		socketThread = new ClientSocketThread(this,ip); // initiate and start new Socket Thread, 
		socketThread.start();							// which handles connection to and communication with server.
		
		playerGrid = new Grid();						// Initiate new grid to handle data about players ships.
		placementController = new PlacementController(this);
	}

	public void newMainGui() {
		gui = new MainGui(this);
	}

	public SocketThread getSocketThread(){
		return socketThread;
	}
	
	public String getName(){
		return userName;
	}

	public void displayMessage(String line) {
		gui.writeMessage(line);
		
	}
	public boolean isPlacing(){
		return isPlacing;
	}
	public void setPlacing(boolean b){
		isPlacing = b;
	}
	public void endPlacement(){
		gui.setShipLabel(null); // clears label
		gui.setShipsLeftLabel(-1); // clears label
		placementController = null;
		isPlacing = false;
		gameController = new GameController(this);
		gameController.setTurn(false);
		socketThread.sendReadyMsg();
		gui.setGameGui();
	}
	public MainGui getGui(){
		return gui;
	}
	public boolean allShipsPlaced() {
		return allShipsPlaced;
	}
	public PlacementController getPlacementController() {
		return placementController;
	}
	public GameController getGameController(){
		return gameController;
	}
	public Grid getGrid(){
		return playerGrid;
	}
	public void setOpponentReady() {
		opponentReady = true;
	}
	public boolean isOpponentReady(){
		return opponentReady;
	}
}
