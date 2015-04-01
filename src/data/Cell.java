package data;

public class Cell {
	private boolean isHit = false;
	private int x,y;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setHit(boolean b){
		this.isHit = b;
	}
	public boolean isHit(){
		return isHit;
	}
}
