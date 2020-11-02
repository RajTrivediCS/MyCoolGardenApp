import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ASCIIGardenModel {
	int xMinLeftBar, yMinLeftBar;
	int xMaxLeftBar, yMaxLeftBar;
	int xMinWorkSpace, yMinWorkSpace;
	int xMaxWorkSpace, yMaxWorkSpace;
	int xWasteBasket;
	int yWasteBasket;
	Garden garden;
	ArrayList<Plant> trashBin;
	ArrayList<Plant> hotBarPlants;
	boolean running = true;
	
	ASCIIGardenModel() throws IOException{
		this.xMinLeftBar = 0;
		this.xMaxLeftBar = 0;
		this.yMinLeftBar = 0;
		this.yMaxLeftBar = 13;
		this.xMinWorkSpace = 1;
		this.xMaxWorkSpace = 9;
		this.yMinWorkSpace = 0;
		this.yMaxWorkSpace = 14;
		this.xWasteBasket = 0;
		this.yWasteBasket = 14;
		this.garden = new Garden();
		this.trashBin = new ArrayList<>();
		this.hotBarPlants = getPlantsListFromFile("plants.txt");
	}
	
	//public void handlePlacement();
	//public void handleDrag();
	//public void handleHoverText(int x, int y);
	public void printGarden(char[][]g) {
		for(int i = 0; i<g.length; i++) {
			for(int j = 0; j<g[0].length; j++) {	
				if(j==0) {
					System.out.print(g[i][j]+"||");
				}else {
					System.out.print(g[i][j]+"   ");
				}
			}
			System.out.println("");
		}
	}
	
	public ArrayList<Plant> getPlantsListFromFile(String filename) throws IOException {
		ArrayList<Plant> plantsList = new ArrayList<>();
		for(int i = 0; i < 14; i++) {
			String currLine = Files.readAllLines(Paths.get(filename)).get(i);
			String[] parts = currLine.split("-");
			plantsList.add(new Plant(parts[0],0,i,parts[1],parts[2]));
		}
		return plantsList;
	}
	
	public char[][] createGrid(){
			char[][]grid=new char[15][10];
			for(char g[] : grid) {
				Arrays.fill(g, '*');
			}
			grid[0][0] = 'a';
			grid[1][0] = 'b';
			grid[2][0] = 'c';
			grid[3][0] = 'd';
			grid[4][0] = 'e';
			grid[5][0] = 'f';
			grid[6][0] = 'g';
			grid[7][0] = 'h';
			grid[8][0] = 'w';
			grid[9][0] = 'j';
			grid[10][0] = 't';
			grid[11][0] = 'y';
			grid[12][0] = 'r';
			grid[13][0] = 'p';
			grid[14][0] = 'X';
			return grid;
	}
	
	public void printOpening() {
		System.out.println("\nEveryting to the right of the double lines is your current garden.");
		System.out.println("To the left of the double-lines is our hotbar. The lower case letters represents plants "
				+ "The 'X' represents the trashbin");
		System.out.println("If user inputs where a lower case letter is, he will get a second prompt to input where he would like to"
				+ "place the flower");
		System.out.println("If user inputs where a plant is in the garden he will get information and a prompt asking if he wants to move it");
		System.out.println("If user moves flower to the capital 'X', plant is deleted. Else, plant is moved");
		System.out.println("Type one of the following: edit, print, fact, or exit: ");
	}
	
	public Plant handleBarSelect(int x, int y) {
		Plant tba = new Plant("if you are seeing this you done goofed", 99, 99); // tba == to be added
		for(Plant p : hotBarPlants) {
			if(p.yLoc==y) {
				System.out.println("You have selected: " + p.name + ". It needs "+ p.plantLight +" and "+ p.plantSoil + 
						". Now select where you want to move it");
				tba=p;
			}
		}
		return tba;
	}
	
	public void handleAddToGarden(int gX, int gY, Plant tba, char[][]grid) {
		garden.addPlant(tba, gX, gY);
		char z = tba.name.charAt(0);
		grid[gY][gX] = z;
	}
	
	public Plant handleSelectingPlantInGarden(int x, int y) {
		Plant tbm = new Plant("no plant", 99, 99);
		for(Plant p : garden.gardensPlants) {
			if(p.xLoc == x && p.yLoc == y) {
				tbm=p;
				System.out.println("You selected a " + p.name + " to be moved. It needs "+ p.plantLight +
						" and "+ p.plantSoil + ". Now where do you want it to go. "
						+ "If you select input 0,14, it will be moved to the trashbin");
			}
		}
		return tbm;
	}
	
	public void handleMoveInGarden(int x, int y, int gX, int gY, Plant tbm, char[][]grid) {
		tbm.updatePlantLocation(gX, gY);
		grid[y][x] = '*';
		char z = tbm.name.charAt(0);
		grid[gY][gX] = z;
	}
	
	public void handleDeletionInGarden(int x, int y, Plant tbm, char[][]grid) {
		garden.deletePlant(tbm);
		grid[y][x] = '*';
		trashBin.add(tbm);
		System.out.println("Deletion successful");
	}
	
	public void handleAddEdit(Scanner input, int x, int y, char[][]grid) {
		Plant tba = handleBarSelect(x, y);
		boolean inside = true;
		while(inside) {
			System.out.println("Choose an x in your garden: ");
			int gX = input.nextInt();
			System.out.println("Good, now choose a y in your garden: ");
			int gY = input.nextInt();
			if(gX>=xMinWorkSpace && gX<=xMaxWorkSpace && gY >=yMinWorkSpace && gY <= yMaxWorkSpace) {
				handleAddToGarden(gX, gY, tba, grid);
				inside=false;
			}
			else {
				System.out.println("Something went wrong. Make sure your x is between 1 and 9 and your y is between 0 and 14");
			}
		}
	}
	
	public void handleMoveEdit(Scanner input, int x, int y, char[][]grid) {
		Plant tbm = handleSelectingPlantInGarden(x,y);
		if(tbm.name.equals("no plant")) {
			System.out.println("You select an area with no plant.");
		}
		else {
			//start of while loop
			boolean inside=true;
			while(inside) {
				System.out.println("Select the xLocation you want this plant to go");
				int gX = input.nextInt();
				System.out.println("Selecy the y location you want this plant to go");
				int gY = input.nextInt();
				if(gX >= xMinWorkSpace && gX<=xMaxWorkSpace && gY >= yMinWorkSpace && gY <= yMaxWorkSpace) {
					handleMoveInGarden(x,y,gX,gY,tbm, grid);
					inside = false;
				}
				else if(gX == xWasteBasket && gY == yWasteBasket) {
					handleDeletionInGarden(x,y,tbm,grid);
					inside = false;
				}
				else {
					System.out.println("The space you selected is not valid. Try again");
				}
			}
		}
	}
	
	public void handleShowTrash() {
		System.out.println("You selected the trashbin. Here's what's inside: ");
		for(Plant p : trashBin) {
			System.out.print(p.name+" ");
		}
		System.out.println("");
	}
	
	public void handleEdit(Scanner input, char[][]grid) {
		System.out.println("\nInput an x coordinate");
		int x = input.nextInt();
		System.out.println("Now input a y coordinate (NOTE: y is going down instead of up)");
		int y = input.nextInt();
		//selecting space on the hotbar
		if(x >= xMinLeftBar && x <= xMaxLeftBar && y >= yMinLeftBar && y <= yMaxLeftBar) {
			handleAddEdit(input, x, y, grid);
		}
		//selecting space in the garden
		else if(x>=xMinWorkSpace && x <= xMaxWorkSpace && y >= yMinWorkSpace && y <= yMaxWorkSpace) {
			handleMoveEdit(input, x, y, grid);
		}
		//selecting trash bin
		else if(x==xWasteBasket && y==yWasteBasket) {
			handleShowTrash();
		}
		else {
			System.out.println("Nice try, but the value you inputed was not within our 15 by 10 grid.");
		}
	}
	
	public void handleFact(Scanner input) {
		System.out.println("Choose the y location of the plant in the left bar that you want to learn about");
		int yLoc = input.nextInt();
		for(Plant p : hotBarPlants) {
			if(p.yLoc==yLoc) {
				System.out.println("You have selected: " + p.name + ". It needs "+ p.plantLight +" and "+ p.plantSoil+".");
			}
		}
	}
	
	public boolean handleExit(Scanner input) {
		System.out.println("Exit program?(yes/no)");
		String exit = input.next();
		if(exit.equals("yes")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static void main(String[] args) throws IOException {
		//create empty garden here which will be an array of array of characters that we will print out later
		//create list of flowers for user to choose from. They will be represented by the first letter of their name in the ASCII Garden.
		//ask user for input on what they would like to do
		//input changes the garden by adding or deleting a flower or moving plant
		//can display tallies too and print them
		
		//***********************
		ASCIIGardenModel model = new ASCIIGardenModel();
		
		char[][]grid = model.createGrid(); //this is kind of like our view.		
		
		System.out.println("Welcome to Team #1's garden application!");
		Scanner input = new Scanner(System.in);
		model.printGarden(grid);
		while(model.running) {
			model.printOpening();
			String command = input.next();
			if(command.equals("edit")) {
				model.handleEdit(input, grid);
			}
			
			else if (command.equals("print")) {
				model.printGarden(grid);
			}
			
			else if (command.equals("fact")) {
				model.handleFact(input);
			}
			else if(command.equals("exit")) {
				model.running = model.handleExit(input);
			}
			else {
				System.out.println("Nice try buddy but that wasn't a choice.");
			}
		} 
	}
}
