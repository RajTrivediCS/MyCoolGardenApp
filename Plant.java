import java.util.*;

public class Plant {
	String name;
	double xLoc;
	double yLoc;
	String plantLight;
	String plantWater;
	String plantSoil;
	
	Plant(String name, double xLoc, double yLoc){
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		//make another constructor in the future
	}
	Plant(String name, double xLoc, double yLoc, String pLight, String pSoil){
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.plantLight = pLight;
		this.plantSoil = pSoil;
		
		//make another constructor in the future
	}
	
	public void updatePlantLocation(double gX, double gY) {
		xLoc = gX;
		yLoc = gY;
	}
}
