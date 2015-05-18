package data;

public enum ShipType {
	
	AIRCRAFT_CARRIER(1,5, "Aircraft Carrier"),
	BATTLESHIP(1,4, "Battleship"),
	CRUISER(1,3, "Cruiser"),
	DESTROYER(2,2, "Destroyer"),
	SUBMARINE(2,1, "Submarine");
	
	private int amount;
	private int size;
	private String name;
	
	private ShipType(int amount, int size, String name){
		this.amount = amount;
		this.size = size;
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public int getSize() {
		return size;
	}
	public String getName() {
		return name;
	}
}
