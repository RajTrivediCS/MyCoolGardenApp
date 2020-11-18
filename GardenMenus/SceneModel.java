package GardenMenus;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SceneModel { 
	static final int NAMESPOT = 0;
	static final int SUNSPOT = 1;
	static final int SOILSPOT = 2;
	ArrayList<Plant> hotBarPlants;

	SceneModel(){
		try {
			this.hotBarPlants = getPlantsListFromFile("plants.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Plant> getPlantsListFromFile(String filename) throws IOException {
		List<Integer> lineNumbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
		ArrayList<Plant> plantsList = new ArrayList<>();
		ListIterator<Integer> itr = lineNumbers.listIterator();
		while(itr.hasNext()) {
			int index = itr.next();
			String currLine = Files.readAllLines(Paths.get(filename)).get(index-1);
			String[] parts = currLine.split("-");
			plantsList.add(new Plant(parts[NAMESPOT],0,index-1,parts[SUNSPOT],parts[SOILSPOT]));
		}
		return plantsList;
	}
	
	public void setHotBar(ArrayList<Plant> plants) {
		this.hotBarPlants = plants;
	}

	public ArrayList<Plant> getHotBarPlants() {
		return this.hotBarPlants;
	}
}