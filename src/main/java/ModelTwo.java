import java.io.File;

import java.io.IOException;
import java.util.*;

public class ModelTwo { 
	static final int NAMESPOT = 0;
	static final int SUNSPOT = 1;
	static final int SOILSPOT = 2;
	static final int SIZESPOT = 3;
	Garden garden;
	ArrayList<Plant> trashBin;
	ArrayList<Plant> hotBarPlants;

	public ModelTwo(){
		this.garden = new Garden();
		this.trashBin = new ArrayList<>();
		try {
			this.hotBarPlants = getPlantsListFromFile("plants.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Plant> getPlantsListFromFile(String filename) throws IOException {
		Scanner sc = new Scanner(new File(filename));
		ArrayList<Plant> plantsList = new ArrayList<>();
		while(sc.hasNextLine()) {
			String currLine = sc.nextLine();
			System.out.println(currLine);
			String[] parts = currLine.split("-");
			plantsList.add(new Plant(parts[NAMESPOT],0,0,parts[SUNSPOT],parts[SOILSPOT],parts[SIZESPOT]));
		}
		return plantsList;
	}
	
	public void addToGarden(double gX, double gY, Plant tba) {
		garden.addPlant(tba, gX, gY);
		//add more info as we work more with the new View
	}

	public void setHotBar(ArrayList<Plant> plants) {
		this.hotBarPlants = plants;
	}

	public ArrayList<Plant> getHotBarPlants() {
		return this.hotBarPlants;
	}

	public Garden getGarden() {
		return this.garden;
	}
}