package gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CellPanel extends JPanel{
	
	private int x,y;
	private Color cellColor = Color.WHITE;
	private boolean isSelected = false;
	private boolean containsShip = false;
	// Muligvis gøre sådan at når en celle markeres fjernes evt. andre markerede? (kun under spil)
	public CellPanel(int x, int y){
		this.x = x;
		this.y = y;
		setBackground(cellColor);
		setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		
		addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	            if(isSelected) setSelected(false);
	            else setSelected(true);
	          } 
	        }); 
	}
	public void shoot(){
		if(containsShip) setHit();
		else setMiss();
	}
	public void setMiss(){
		cellColor = Color.BLUE;
		setBackground(cellColor);
	}
	public void setHit(){
		cellColor = Color.RED;
		setBackground(cellColor);
	}
	public void setShip(){
		containsShip = true;
		cellColor = Color.BLACK;
		setBackground(cellColor);
	}
	public void setSelected(boolean n){
		if(!n){
			isSelected = false;
			setBackground(cellColor);
		}
		if(n){
			isSelected = true;
			setBackground(Color.LIGHT_GRAY);
		}
	}
	public boolean isSelected(){
		return isSelected;
	}
	public int getColumn(){
		return x;
	}
	public int getRow(){
		return y;
	}
	public boolean containsShip(){
		return containsShip;
	}
	@Override
	public String toString(){
		return "Cell at + [" + x + ", " + y + "]";
	}
}
