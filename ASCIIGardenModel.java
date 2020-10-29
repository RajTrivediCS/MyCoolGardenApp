import java.util.*;

public class ASCIIGardenModel {
	int xMinLeftBar, yMinLeftBar;
	int xMaxLeftBar, yMaxLeftBar;
	int xMinWorkSpace, yMinWorkSpace;
	int xMaxWorkSpace, yMaxWorkSpace;
	int xWasteBasket;
	int yWasteBasket;
	
	boolean running = true;
	
	ASCIIGardenModel(){
		this.xMinLeftBar = 0;
		this.xMaxLeftBar = 0;
		this.yMinLeftBar = 0;
		this.yMaxLeftBar = 13;
		this.xMinWorkSpace=1;
		this.xMaxWorkSpace=9;
		this.yMinWorkSpace=0;
		this.yMaxWorkSpace=14;
		this.xWasteBasket=0;
		this.yWasteBasket=14;
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
	
	
	public static void main(String[] args) {
		//create empty garden here which will be an array of array of characters that we will print out later
		//create list of flowers for user to choose from. They will be represented by the first letter of their name in the ASCII Garden.
		//ask user for input on what they would like to do
		//input changes the garden by adding or deleting a flower or moving plant
		//can display tallies too and print them
		
		//***********************
		//create bar
		ArrayList<Plant> hotBarPlants = new ArrayList();
		hotBarPlants.add(new Plant("autumn bentgrass",0,0,"any sun","clay or loamy soil"));
		hotBarPlants.add(new Plant("bushy bluestem",0,1,"full or partial sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("crossvine",0,2,"full or partial sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("devil's walking stick",0,3,"full or partial sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("ebony spleenwart",0,4,"any sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("fern (sensitive)",0,5,"any sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("giant plumegrass",0,6,"full or partial sun","loamy or sandy soil"));
		hotBarPlants.add(new Plant("heart leaved aster",0,7,"partial sun or shade","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("white snakeroot",0,8,"any sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("joe-pye weed",0,9,"full or partial sun","sandy soil"));
		hotBarPlants.add(new Plant("tall coneflower",0,10,"full or partial sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("yellow sneezeweed",0,11,"any sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("red chokeberry",0,12,"full or partial sun","clay or loamy or sandy soil"));
		hotBarPlants.add(new Plant("purple false foxglove",0,13,"full sun","sandy soil"));
		
		//create empty trash can
		ArrayList<Plant> trashBin = new ArrayList();
		
		//create garden
		Garden garden = new Garden();
		
		//creating grid
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
		
		
		ASCIIGardenModel model = new ASCIIGardenModel();
		
		System.out.println("Welcome to Team #1's garden application!");
		//FIXME print garden here
		Scanner input = new Scanner(System.in);
		model.printGarden(grid);
		while(model.running) {
			System.out.println("\nEveryting to the right of the double lines is your current garden.");
			System.out.println("To the left of the double-lines is our hotbar. The lower case letters represents plants "
					+ "The 'X' represents the trashbin");
			System.out.println("If user inputs where a lower case letter is, he will get a second prompt to input where he would like to"
					+ "place the flower");
			System.out.println("If user inputs where a plant is in the garden he will get information and a prompt asking if he wants to move it");
			System.out.println("If user moves flower to the capital 'X', plant is deleted. Else, plant is moved");
			System.out.println("Type one of the following: edit, print, fact, or exit: ");
			//get command
			String command = input.next();
			if(command.equals("edit")) {
				System.out.println("\nInput an x coordinate");
				int x = input.nextInt();
				System.out.println("Now input a y coordinate (NOTE: y is going down instead of up)");
				int y = input.nextInt();
				
				//selecting space on the hotbar
				if(x==0 && y<=13) {
					Plant tba = new Plant("if you are seeing this you done goofed", 99, 99); // tba == to be added
					for(Plant p : hotBarPlants) {
						if(p.yLoc==y) {
							System.out.println("You have selected: " + p.name + ". It needs "+ p.plantLight +" and "+ p.plantSoil + 
									". Now select where you want to move it");
							tba=p;
							break;
						}
					}
					boolean inside = true;
					while(inside) {
						System.out.println("Choose an x in your garden: ");
						int gX = input.nextInt();
						System.out.println("Good, now choose a y in your garden: ");
						int gY = input.nextInt();
						//FIXME: replace with variables in constructor later
						if(gX>=1 && gX<=9 && gY >=0 && gY <= 14) {
							garden.addPlant(tba, gX, gY);
							char z = tba.name.charAt(0);
							grid[gY][gX] = z;
							inside=false;
						}
						else {
							System.out.println("Something went wrong. Make sure your x is between 1 and 9 and your y is between 0 and 14");
						}
					}
				}
				
				//selecting space in the garden
				else if(x>0 && x<=9 && y>=0 && y<=14) {
					boolean plantThere = false;
					Plant tbm = new Plant("if you see this YOU DONE GOOFED", 99, 99);
					for(Plant p : garden.gardensPlants) {
						if(p.xLoc == x && p.yLoc == y) {
							tbm=p;
							plantThere=true;
							System.out.println("You selected a " + p.name + " to be moved. It needs "+ p.plantLight +
									" and "+ p.plantSoil + ". Now where do you want it to go. "
									+ "If you select input 0,14, it will be moved to the trashbin");
							
							//start of while loop
							boolean inside=true;
							while(inside) {
								System.out.println("Select the xLocation you want this plant to go");
								int gX = input.nextInt();
								System.out.println("Selecy the y location you want this plant to go");
								int gY = input.nextInt();
								if(gX>=1 && gX<=9 && gY >= 0 && gY <= 14) {
									tbm.updatePlantLocation(gX, gY);
									grid[y][x] = '*';
									char z = tbm.name.charAt(0);
									grid[gY][gX] = z;
									inside = false;
								}
								else if(gX == 0 && gY == 14) {
									garden.deletePlant(tbm);
									grid[y][x] = '*';
									trashBin.add(tbm);
									System.out.println("Deletion successful");
									inside = false;
								}
								else {
									System.out.println("The space you selected is not valid. Try again");
								}
							}
							break;
							//end of while loop
							
						}
					}
					if(!plantThere) {
						System.out.println("You select an area with no plant.");
					}
					
				}
				//selecting trash bin
				else if(x==0 && y==14) {
					System.out.println("You selected the trashbin. Here's what's inside: ");
					for(Plant p : trashBin) {
						System.out.print(p.name+" ");
					}
					System.out.println("");
				}
				else {
					System.out.println("Nice try, but the value you inputed was not within our 15 by 10 grid.");
				}
			}
			else if (command.equals("print")) {
				model.printGarden(grid);
			}
			
			else if (command.equals("fact")) {
				System.out.println("Choose the y location of the plant in the left bar that you want to learn about");
				int yLoc = input.nextInt();
				for(Plant p : hotBarPlants) {
					if(p.yLoc==yLoc) {
						System.out.println("You have selected: " + p.name + ". It needs "+ p.plantLight +" and "+ p.plantSoil+".");
					}
				}
			}
			else if(command.equals("exit")) {
				System.out.println("Exit program?(yes/no)");
				String exit = input.next();
				if(exit.equals("yes")) {
					model.running = false;
				}
			}
			else {
				System.out.println("Nice try buddy but that wasn't a choice.");
			}
		}
	}
}
