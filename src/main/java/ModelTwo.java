import java.io.File;

import java.io.IOException;
import java.util.*;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ModelTwo { 
	static final int NAMESPOT = 0;
	static final int SUNSPOT = 1;
	static final int SOILSPOT = 2;
	static final int SIZESPOT = 3;
	Garden garden;
	ArrayList<Plant> trashBin;
	ArrayList<Plant> hotBarPlants;

	/***
	 * Initializes the instance variables for this class
	 */
	public ModelTwo(){
		this.garden = new Garden();
		this.trashBin = new ArrayList<>();
		try {
			this.hotBarPlants = getPlantsListFromFile("plants.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Retrieves the List of plants from text file and returns it at the end
	 * @param filename the name of file to retrieve List of plants
	 * @return the List of plants after retrieving from File
	 * @throws IOException
	 */
	public ArrayList<Plant> getPlantsListFromFile(String filename) throws IOException {
		Scanner sc = new Scanner(new File(filename));
		ArrayList<Plant> plantsList = new ArrayList<>();
		while(sc.hasNextLine()) {
			String currLine = sc.nextLine();
			String[] parts = currLine.split("-");
			plantsList.add(new Plant(parts[NAMESPOT],0,0,parts[SUNSPOT],parts[SOILSPOT],parts[SIZESPOT]));
		}
		return plantsList;
	}

/*	NEEDS TO WORKED ON...
	public void handleDeletionInGarden(double x, double y, Plant tbm) {
		garden.deletePlant(tbm);
		trashBin.add(tbm);
		System.out.println("Deletion successful");
	}
	
	public void handleShowTrash() {
		System.out.println("You selected the trashbin. Here's what's inside: ");
		for(Plant p : trashBin) {
			System.out.print(p.name+" ");
		}
		System.out.println("");
	}
*/	
	/***
	 * Adds the plant with specified x and y coordinates into the Garden
	 * @param gX the x coordinate for plant
	 * @param gY the y coordinate for plant
	 * @param tba the plant to be added
	 */
	public void addToGarden(double gX, double gY, Plant tba) {
		garden.addPlant(tba, gX, gY);
	}

	/***
	 * Sets the current List of plants in the SideView with the given List of plants
	 * @param plants the List of plants to be set for
	 */
	public void setHotBar(ArrayList<Plant> plants) {
		this.hotBarPlants = plants;
	}

	/***
	 * Returns the most recent state of List of plants in the SideView
	 * @return the current instance for the List of plants in the SideView
	 */
	public ArrayList<Plant> getHotBarPlants() {
		return this.hotBarPlants;
	}

	/***
	 * Returns the most recent state of Garden
	 * @return the current instance of Garden
	 */
	public Garden getGarden() {
		return this.garden;
	}

	public void setGarden(Garden userSavedGarden) {
		garden = userSavedGarden;
	}
}