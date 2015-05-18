package data;

import gui.CellPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid {
	private Cell[][] cellArr;
	private List<Ship> ships ;
	
	public Grid(){
		ships = new ArrayList<Ship>();
		cellArr = new Cell[10][10];
		for(int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				cellArr[i][j] = new Cell(i,j);
			}
		}
	}
	public void placeShip(CellPanel[] placement, String name){
		Cell[] tempCells = new Cell[placement.length];
		Ship tempShip = new Ship(placement.length, name);
		for(int i = 0; i < placement.length; i++){
			tempCells[i] = new Cell(placement[i].getColumn(),placement[i].getRow());
		}
		tempShip.setPlacement(tempCells);
		ships.add(tempShip);
	}
	
	public void hitCell(int x, int y){
		cellArr[x][y].setHit(true);
	}
	public List<Ship> getShips(){
		return ships;
	}
}
