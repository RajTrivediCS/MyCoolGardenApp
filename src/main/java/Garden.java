import java.io.File;
import java.util.*;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class Garden implements java.io.Serializable{
	ArrayList<Plant> gardensPlants = new ArrayList<>();
	String gardenLight;
	String gardenWater;
	String gardenSoil;
	String bg;
	
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
	 * Returns the most recent Background Image file
	 * @return the File with the Background Image
	 */
	public String getBg() {
		return bg;
	}

	/***
	 * Sets the current Background Image file with the given Background Image File
	 * @param bg the File to set as a Background Image for a Garden
	 */
	public void setBg(String bg) {
		this.bg = bg;
	}

	/***
	 * Removes the given Plant from Garden
	 * @param p the Plant to be removed from Garden
	 */
	public void deletePlant(Plant p) {
		gardensPlants.remove(p);
	}

/* NEEDS TO BE WORKED ON...
	public int tallyGardenScore() {
		return 0;
	}
	
	public String writeGardenScore() {
		return "";
	}
	
	public String sumGarden() {
		return "";
	}
*/	
	/***
	 * Sets the current List of plants with the given List of plants 
	 * @param plants the List of plants to be initialized 
	 */
	public void setGardensPlants(ArrayList<Plant> plants) {
		this.gardensPlants = plants;
	}

	public ArrayList<Plant> getGardensPlants() {
		return gardensPlants;
	}

	public void setGardensSoil(String string) {
		gardenSoil = string;
		
	}

	public void setGardensLight(String string) {
		gardenLight = string;
	}

	public String getGardensLight() {
		return gardenLight;
	}

	public String getGardensSoil() {
		return gardenSoil;
	}
}