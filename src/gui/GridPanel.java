package gui;

import java.awt.GridLayout;
import java.util.TreeSet;

import javax.swing.JPanel;

import data.Grid;
import data.Ship;
import data.ShipType;

/*
 * Vi skal måske have to klasser der begge extender GridPanel - en til modstanderen og en til spilleren?
 * */
public class GridPanel extends JPanel {
	private CellPanel[][] grid = new CellPanel[10][10];
	
	public GridPanel(){
		/*
		 * Instantiates the grid of cell panels
		 * */
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				grid[i][j] = new CellPanel(i,j);
			}
		}
		/*
		 * Adds the cell panels to the grid panel
		 * */
		setLayout(new GridLayout(10,10));
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				add(grid[j][i]);
			}
		}
	}
	/*
	 * Methods for ship placement. Need a way to determine if the selected cells are legal, as in:
	 * if more than 1 selected, are they only on either one row or column
	 * and are they all next to each other.
	 * */
	// returns number of selected cellpanels
	private int getTotalSelected() {
		int totalSelected = 0;
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(grid[i][j].isSelected())totalSelected++;
			}
		}
		return totalSelected;
	}
	// returns array of selected cellpanels
	public CellPanel[] getSelected(){
		int idx = 0;
		CellPanel[] currentSelection = new CellPanel[getTotalSelected()];
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(grid[i][j].isSelected()){
					currentSelection[idx] = grid[i][j];
					idx++;
				}
			}
		}
		return currentSelection;
	}
	/*
	 * Checks if the selected cell panels are adjacent. First checks if there all cells are placed on one axis
	 * 
	 * Uses Treemap because it is ordered. And set in general, to see how many different x values and y values are selected.
	 * */
	private boolean isAdjacent(CellPanel[] selectedCells, ShipType type){
		TreeSet<Integer> xValues = new TreeSet<Integer>();
		TreeSet<Integer> yValues = new TreeSet<Integer>();
		for(int i = 0; i < selectedCells.length; i++){
			xValues.add(selectedCells[i].getColumn());
			yValues.add(selectedCells[i].getRow());
		}
		if(xValues.size() == 1){ // if all the Cells only have one x values - All of them are on the same line.
			if(yValues.last() - yValues.first() + 1 == type.getSize()){ // If the largest y values minus the smallest y value equals the ship size
				return true;
			}
			else{
				return false;
			}
		}
		if(yValues.size() == 1){ // if all the Cells only have one y values - All of them are on the same line.
			if(xValues.last() - xValues.first() + 1 == type.getSize()){ // If the largest x values minus the smallest x value equals the ship size
				return true;
			}
			else{
				return false;
			}
		}
		else return false;
	}
	/*
	 * Checks if the selected cells already contains a ship
	 * */
	public boolean selectedIsNotFilled(){
		for(CellPanel cp : getSelected()){
			if(cp.containsShip()){
				return false;
			}
		}
		return true;
	}
	/*
	 * Returns true if the currently selected cell matches the size of current ship, and is placed legally
	 * */
	public boolean placementLegal(ShipType currentShip) {
		// Logic to determine if ship is placed legally
		if(selectedIsNotFilled()){
			if(currentShip.getSize() == 1 && getTotalSelected() == 1){ // if ship is size 1, any placement of a single cell is accepted
				return true;
			} 
			else if(currentShip.getSize() == getTotalSelected() && isAdjacent(getSelected(), currentShip)) 
			{
				return true;
			} 
			else return false;
		}
		else return false;
	}
	// places ship on the gui
	public void placeShip(CellPanel[] shipCells){
		for(CellPanel cp : shipCells){
			grid[cp.getColumn()][cp.getRow()].setShip();
		}
		deselectAll();
		repaint();
	}
	public void deselectAll(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				grid[i][j].setSelected(false);
			}
		}
	}
	public CellPanel getCell(int x, int y){
		return grid[x][y];
	}
}