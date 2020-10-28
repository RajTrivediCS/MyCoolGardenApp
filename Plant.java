import java.util.*;

public class Plant {
	String name;
	int xLoc;
	int yLoc;
	String gardenLight;
	String gardenWater;
	String gardenSoil;
	
	Plant(String name, int xLoc, int yLoc){
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		//make another constructor in the future
	}
	
	public void updatePlantLocation(int newX, int newY) {
		xLoc = newX;
		yLoc = newY;
	}
}
