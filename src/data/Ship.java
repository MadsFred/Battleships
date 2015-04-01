package data;

public class Ship {
	private String name;
	private Cell[] placement;
	
	public Ship(int size, String name){
		this.name = name;
		placement = new Cell[size];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cell[] getPlacement() {
		return placement;
	}

	public void setPlacement(Cell[] placement) {
		this.placement = placement;
	}
	
	
}
