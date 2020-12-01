import java.io.File;
import java.util.*;


public class Garden implements java.io.Serializable{
	ArrayList<Plant> gardensPlants = new ArrayList<>();
	String gardenLight;
	String gardenWater;
	String gardenSoil;
	File bg;
	
	/***
	 * Adds the given Plant to a specified x and y coordinates
	 * @param p the Plant to be added
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void addPlant(Plant p, double x, double y) {
		this.gardensPlants.add(new Plant(p.name, x, y, p.plantLight, p.plantSoil, p.plantSize));
	}

	/***
	 * 
	 * @return the File with the Background Image
	 */
	public File getBg() {
		return bg;
	}

	/***
	 * Sets the current Background Image file with the given Background Image File
	 * @param bg the File to set as a Background Image for a Garden
	 */
	public void setBg(File bg) {
		this.bg = bg;
	}

	/***
	 * Removes the given Plant from Garden
	 * @param p the Plant to be removed from Garden
	 */
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
	
	/***
	 * Sets the current List of plants with the given List of plants 
	 * @param plants the List of plants to be initialized 
	 */
	public void setGardensPlants(ArrayList<Plant> plants) {
		this.gardensPlants = plants;
	}
}