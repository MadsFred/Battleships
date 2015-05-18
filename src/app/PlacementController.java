package app;

import app.interfaces.AppController;
import data.ShipType;

public class PlacementController {
	
	private AppController appController;
	
	private int shipIndex = 0;
	private int amountThisShipPlaced = 0;
	private ShipType[] shipTypes;
	
	public PlacementController(AppController appController){
		this.appController = appController;
		shipTypes = new ShipType[ShipType.values().length];
		shipTypes = ShipType.values();
		appController.getGui().setShipLabel(shipTypes[shipIndex]);
		appController.getGui().setShipsLeftLabel(shipTypes[shipIndex].getAmount() - amountThisShipPlaced);
	}
	// Bugged out. Fix it! Something with the order and such. Pretty Close, though.
	public void nextShip(){
		int amountOfShips = shipTypes[shipIndex].getAmount();
		
		if(shipIndex>=shipTypes.length - 1 && amountThisShipPlaced >= amountOfShips - 1){
			appController.endPlacement();
		}
		else{
			
			if(amountThisShipPlaced >= amountOfShips - 1){
				shipIndex++;
				amountThisShipPlaced = 0;
				appController.getGui().setShipLabel(shipTypes[shipIndex]);
				appController.getGui().setShipsLeftLabel(shipTypes[shipIndex].getAmount() - amountThisShipPlaced);
			}
			else{
				amountThisShipPlaced++;
				appController.getGui().setShipsLeftLabel(shipTypes[shipIndex].getAmount() - amountThisShipPlaced);
			}
		}
	}
	public ShipType getCurrentShip() {
		return shipTypes[shipIndex];
	}
}
