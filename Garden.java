import java.util.*;

public class Garden {
	ArrayList<Plant> gardensPlants = new ArrayList();
	String gardenLight;
	String gardenWater;
	String gardenSoil;
	
	public void addPlant(Plant p, double x, double y) {
		this.gardensPlants.add(new Plant(p.name, x, y, p.plantLight, p.plantSoil));
	}

	public void deletePlant(Plant p) {
		gardensPlants.remove(p);
	}
	
	public int tallyGardenScore() {
		return 0;
	}
	
	public String writeGardenScore() {
		return "";
	}
	
	public String sumGarden() {
		return "";
	}
}
