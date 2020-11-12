import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Model implements java.io.Serializable  { 
	static final int NAMESPOT = 0;
	static final int SUNSPOT = 1;
	static final int SOILSPOT = 2;
	Garden garden;
	ArrayList<Plant> trashBin;
	ArrayList<Plant> hotBarPlants;

	Model(){
		this.garden = new Garden();
		this.trashBin = new ArrayList<>();
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
	
	public void sortFlowersByCriteria(int criteria) {
		if(criteria == NAMESPOT)
			sortFlowersByName(hotBarPlants);
		else if(criteria == SUNSPOT)
			sortFlowersByLight(hotBarPlants);
		else if(criteria == SOILSPOT)
			sortFlowersBySoil(hotBarPlants);
		else
			System.out.println("Invalid criteria to sort flowers...");
	}
	
	public void sortFlowersByName(List<Plant> flowers) {
		Collections.sort(flowers,new Comparator<Plant>(){
			@Override
			public int compare(Plant p1, Plant p2) {
				return p1.name.compareTo(p2.name);
			}
			
		});
		
	}
	
	public void sortFlowersByLight(List<Plant> flowers) {
		Collections.sort(flowers,new Comparator<Plant>(){
			@Override
			public int compare(Plant p1, Plant p2) {
				int cmp = p1.plantLight.compareTo(p2.plantLight);
				if(cmp == 0) {
					return p1.name.compareTo(p2.name);
				}
				else {
					return cmp;
				}
			}
			
		});
	}
	public void sortFlowersBySoil(List<Plant> flowers) {
		Collections.sort(flowers,new Comparator<Plant>(){
			@Override
			public int compare(Plant p1, Plant p2) {
				int cmp = p1.plantSoil.compareTo(p2.plantSoil);
				if(cmp == 0) {
					return p1.name.compareTo(p2.name);
				}
				else {
					return cmp;
				}
			}
		});
	}

	
	public void addToGarden(double gX, double gY, Plant tba) {
		garden.addPlant(tba, gX, gY);
		//add more info as we work more with the new View
	}
	
	public Plant selectPlantInGarden(double x, double y) {
		Plant tbm = new Plant("no plant", 99, 99);
		for(Plant p : garden.gardensPlants) {
			if(p.xLoc == x && p.yLoc == y) {
				tbm=p;
			}
		}
		return tbm;
	}
	
	public void moveInGarden(double gX, double gY, Plant tbm) {
		tbm.updatePlantLocation(gX, gY);
		//add more info as we work more with the new View
	}
	
	public void handleDeletionInGarden(double x, double y, Plant tbm) {
		garden.deletePlant(tbm);
		trashBin.add(tbm);
		//add more info as we work more with the new View
		System.out.println("Deletion successful");
	}
	
	
	public void handleShowTrash() {
		System.out.println("You selected the trashbin. Here's what's inside: ");
		for(Plant p : trashBin) {
			System.out.print(p.name+" ");
		}
		System.out.println("");
	}

	public void setHotBar(ArrayList<Plant> plants) {
		this.hotBarPlants = plants;
	}

	public ArrayList<Plant> getHotBarPlants() {
		return this.hotBarPlants;
	}

	public Object getGarden() {
		return this.garden;
	}
}