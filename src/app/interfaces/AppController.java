package app.interfaces;

import gui.MainGui;
import app.GameController;
import app.PlacementController;
import data.Grid;

public interface AppController {
	public MainGui getGui();
	public void endPlacement();
	public SocketThread getSocketThread();
	public String getName();
	public boolean isPlacing();
	public boolean allShipsPlaced();
	public PlacementController getPlacementController();
	public GameController getGameController();
	public Grid getGrid();
	public void setOpponentReady();
	public boolean isOpponentReady();
}
