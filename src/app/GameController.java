package app;

import java.util.List;

import javax.swing.JOptionPane;

import app.interfaces.AppController;
import data.Cell;
import data.Ship;
import data.ShipType;

public class GameController {

	private AppController appController;
	private boolean hasTurn = false;
	private int shipsLeft = 0;
	private boolean opponentReadyNextGame = false;
	
	public GameController(AppController appController) {
		this.appController = appController;
		
		for(ShipType st : ShipType.values()){
			shipsLeft+=st.getAmount(); // finder total antal skibe
		}
	}
	public void setTurn(boolean b) {
		this.hasTurn = b;
		appController.getGui().setTurnLabel(b);
	}
	public boolean hasTurn() {
		return hasTurn;
	}
	public void checkHit(int x, int y) {
		List<Ship> shipList = appController.getGrid().getShips();
		boolean hit = false;
		Ship hitShip = null;
		for(Ship s : shipList){
			Cell[] cellArr = s.getPlacement();
			for(int i = 0; i < cellArr.length; i++){
				if(x==cellArr[i].getX() && y==cellArr[i].getY()){
					s.getPlacement()[i].setHit(true);
					appController.getSocketThread().hit(x,y);  // send message that shot hit a ship back.
					// ship is hit at x, y.
					appController.getGui().getPlayerGrid().getCell(x, y).setHit();
					hit = true;
					hitShip = s;
				}
			}
		}
		if(hit){
			Cell[] cellArr = hitShip.getPlacement();
			int amount = 0;
			for(int i = 0; i < cellArr.length; i++){
				if(cellArr[i].isHit()){
					amount++;
				}
			}
			if(amount>=cellArr.length){
				// Ship has been sunken!
				appController.getSocketThread().sendChatMessage(appController.getName() + "'s " + hitShip.getName() + " has been sunken!");
				shipsLeft-=1;
				if(shipsLeft<=0){
					lostGame();
					}
			}
		}
		else{
			appController.getSocketThread().miss(x,y);
			appController.getGui().getPlayerGrid().getCell(x, y).setMiss();
		}
	}
	private void lostGame() {
		// TODO skal have sin egen besked i protokol.
		appController.getSocketThread().playerLost();
		appController.getSocketThread().sendChatMessage("You won the Game!");
		appController.getGui().writeMessage("You lost the game!");;
		
	}
	public void opponentHit(int x, int y) {
		appController.getGui().getOpponentGrid().getCell(x, y).setHit();
	}
	public void opponentMiss(int x, int y){
		appController.getGui().getOpponentGrid().getCell(x, y).setMiss();
	}
	public void endOfGame(String status){
		int answer = JOptionPane.showConfirmDialog(appController.getGui(), "You " + status + " the game. Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
		if(answer==JOptionPane.YES_OPTION){
//			what happens here ? hvordan sørger vi for at kunne afvente hinanden? Lave et vindue med en cancel knap der er der indtil man modtager et ready fra modstanderen?
		}
		if(answer==JOptionPane.NO_OPTION){
			System.exit(-1);
		}
	}
	
}
