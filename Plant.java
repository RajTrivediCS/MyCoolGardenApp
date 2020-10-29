import java.util.*;

public class Plant {
	String name;
	int xLoc;
	int yLoc;
	String plantLight;
	String plantWater;
	String plantSoil;
	
	Plant(String name, int xLoc, int yLoc){
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		//make another constructor in the future
	}
	Plant(String name, int xLoc, int yLoc, String pLight, String pSoil){
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.plantLight = pLight;
		this.plantSoil = pSoil;
		
		//make another constructor in the future
	}
	
	public void updatePlantLocation(int newX, int newY) {
		xLoc = newX;
		yLoc = newY;
	}
}
