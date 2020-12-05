import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ViewTwo {
	ArrayList<PlantImageView> sideView = new ArrayList<PlantImageView>();;
	ArrayList<PlantImageView> plantsInGarden = new ArrayList<PlantImageView>();	
	ArrayList<PlantImageView> plantsInWasteBasket = new ArrayList<PlantImageView>();
	Stage popUp;
	GridPane gp;
	AnchorPane ap;
	BorderPane bp;
	ScrollPane sp;
	Menu viewMenu;
	Menu fileMenu;
	Menu undoMenu;
	Menu redoMenu;
	MenuBar menuBar;
	MenuButton sortBy;
	Button nameButton;
	Button gSoilButton;
	Button gLightButton;
	Button sunButton;
	Button soilButton;
	Button newButton;
	Button loadButton;
	Button saveButton;
	Button sizeButton;
	Button generateReport;
	BackgroundImage FlowPaneBG;
	HBox hbox;
	VBox vbox;
	Scene scene;
	Image undoImage = new Image("img/undoImage.png");
	Image redoImage = new Image("img/redoImage.png");
	ImageView wasteBasket = new ImageView("https://www.freeiconspng.com/thumbs/recycle-bin-icon/recycle-bin-icon-31.png");
	ImageView undoImgView;
	ImageView redoImgView;
	Stage stage;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;

	/***
	 * Creates the buttons for "Sort By" Menu and places them on the given GridPane
	 * @param grid the GridPane to include all buttons
	 */
	public void buttonMaker(GridPane grid) {		
    	sortBy = new MenuButton("Sort by");
    	
    	nameButton = new Button("Name");
		CustomMenuItem nameItem = new CustomMenuItem(nameButton);
		sortBy.getItems().add(nameItem);
		nameItem.setHideOnClick(false);
    	
		soilButton = new Button("Soil");
		CustomMenuItem soilItem = new CustomMenuItem(soilButton);
		sortBy.getItems().add(soilItem);
		soilItem.setHideOnClick(false);
		
		sunButton = new Button("Sun");
		CustomMenuItem sunItem = new CustomMenuItem(sunButton);
		sortBy.getItems().add(sunItem);
		sunItem.setHideOnClick(false);
		
		sizeButton = new Button("Size");
		CustomMenuItem sizeItem = new CustomMenuItem(sizeButton);
		sortBy.getItems().add(sizeItem);
		sizeItem.setHideOnClick(false);
	
		grid.getChildren().add(sortBy);
	}
	

	/***
	 * Creates all the Menus for ViewTwo Scene and places all of them in the VBox at the end
	 */
	public void topMenuMaker() {
		Menu fileMenu = new Menu("File");
		Menu viewMenu = new Menu("View");
		
		newButton = new Button("New");
		CustomMenuItem newItem = new CustomMenuItem(newButton);
		fileMenu.getItems().add(newItem);
		
		loadButton = new Button("Load");
		CustomMenuItem loadItem = new CustomMenuItem(loadButton);
		fileMenu.getItems().add(loadItem);
		
		saveButton = new Button("Save");
		CustomMenuItem saveItem = new CustomMenuItem(saveButton);
		fileMenu.getItems().add(saveItem);
		
		gLightButton = new Button("Choose Garden Light");
		CustomMenuItem gLightItem = new CustomMenuItem(gLightButton);
		fileMenu.getItems().add(gLightItem);
		
		gSoilButton = new Button("Choose Garden Soil");
		CustomMenuItem gSoilItem = new CustomMenuItem(gSoilButton);
		fileMenu.getItems().add(gSoilItem);
		
		
		Button togglegridButton = new Button("Toggle Grid");
		CustomMenuItem togglegridItem = new CustomMenuItem(togglegridButton);
		viewMenu.getItems().add(togglegridItem);
		
		Button togglebackgroundButton = new Button("Toggle Background");
		CustomMenuItem togglebackgroundItem = new CustomMenuItem(togglebackgroundButton);
		viewMenu.getItems().add(togglebackgroundItem);
		
		generateReport = new Button("Generate Garden Report");
		CustomMenuItem generateReportItem = new CustomMenuItem(generateReport);
		viewMenu.getItems().add(generateReportItem);
		
		MenuBar topBar = new MenuBar();
		
		topBar.getMenus().add(fileMenu);
		topBar.getMenus().add(viewMenu);
		vbox = new VBox(topBar);
		
	}
	
	/***
	 * Adds the Images for the given List of plants in the SideView with hover-over implementation
	 * @param plants the List of plants to add Images for
	 */
	public void plantIVAdder(ArrayList<Plant> plants) {
		int i=0;
		for(Plant p : plants) {
			System.out.println(p.name);
			Image im1 = new Image("img/"+p.name+".png");
			PlantImageView piv = new PlantImageView(p);
			piv.setImage(im1); //write function to change to a plant later
	    	piv.setPreserveRatio(true);
	    	//caseHandler
	    	switch(p.plantSize) {
	    		case "small": piv.setFitHeight(90);
	    		break;
	    		case "medium": piv.setFitHeight(100);
	    		break;
	    		case "large": piv.setFitHeight(130);
	    		break;
	    	}
	    	Tooltip tooltip =  new Tooltip("This is "+p.name+".\n"+"It needs "+p.plantLight+" and "+p.plantSoil+".");
	    	Tooltip.install(piv, tooltip);
	    	piv.setPaneLoc("grid");
			sideView.add(piv);
	    	gp.add(piv, 0, i+1);
	    	i++;
	    }
		wasteBasket.setFitHeight(90);
		wasteBasket.setFitWidth(90);
		gp.add(wasteBasket, 0, i+1);
		
	}
	
	/***
	 * Sorts List of plant image view by name of plant
	 */
	public void nameSort() {
		Collections.sort(sideView,new Comparator<PlantImageView>(){
			@Override
			public int compare(PlantImageView p1, PlantImageView p2) {
				return p1.plant.name.compareTo(p2.plant.name);
			}
		});
	}
	
	/***
	 * Sorts List of plant image view by amount of sunlight
	 */
	public void sunSort() {
		Collections.sort(sideView,new Comparator<PlantImageView>(){
			@Override
			public int compare(PlantImageView p1, PlantImageView p2) {
				int i = p1.plant.plantLight.compareTo(p2.plant.plantLight);
				if(i==0) {
					return p1.plant.name.compareTo(p2.plant.name);
				}
				else {
					return i;
				}
			}
		});
	}
	
	/***
	 * Sorts List of plant image view by amount of soil
	 */
	public void soilSort() {
		Collections.sort(sideView,new Comparator<PlantImageView>(){
			@Override
			public int compare(PlantImageView p1, PlantImageView p2) {
				int i = p1.plant.plantSoil.compareTo(p2.plant.plantSoil);
				if(i==0) {
					return p1.plant.name.compareTo(p2.plant.name);
				}
				else {
					return i;
				}
			}				
		});
	}
	
	/***
	 * Sorts List of plant image view by size
	 */
	public void sizeSort() {
		Collections.sort(sideView,new Comparator<PlantImageView>(){
			@Override
			public int compare(PlantImageView p1, PlantImageView p2) {
				int p1Size = 0;
				int p2Size = 0;
				switch(p1.plant.plantSize) {
					case "small":
						p1Size = 1;
						break;
					case "medium":
						p1Size = 2;
						break;
					case "large":
						p1Size = 3;
						break;
				}
				switch(p2.plant.plantSize) {
					case "small":
						p2Size = 1;
						break;
					case "medium":
						p2Size = 2;
						break;
					case "large":
						p2Size = 3;
						break;
				}
				int i = p1Size - p2Size;
				if(i==0) {
					return p1.plant.name.compareTo(p2.plant.name);
				}
				else {
					return i;
				}
			}				
		});
	}
	
	/***
	 * Uses sort mode to sort the List of plant image view
	 * @param sortMode the mode(either "name", "sun", "soil", or "name") for sorting all Plants
	 */
	public void sideViewSortHelper(String sortMode) { // convert to enum in the future
		switch(sortMode) {
			case "name": nameSort();
			break;
			case "sun" : sunSort();
			break;
			case "soil": soilSort();
			break;
			case "size": sizeSort();
			break;
		}
	}
	
	/***
	 * Sorts the ImageViews in the SideBar based on the given sort mode
	 * @param sortMode the mode(either "name", "sun", "soil", or "name") for sorting all Plants 
	 */
	public void sortSideView(String sortMode) {
		for(PlantImageView p: sideView) {
			gp.getChildren().remove(p);
		}
		sideViewSortHelper(sortMode);
		GridPane sortedGrid = new GridPane();
    	sortedGrid.setMaxWidth(1);
    	sortedGrid.setStyle("-fx-background-color: #ADD8E6");
		buttonMaker(sortedGrid);
		int i = 0;
    	for(PlantImageView p: sideView) {
    		gp.add(p,0,i+1);
    		i++;
    	}
	}
	
	/***
	 * Sets the given file as Background Image and returns it
	 * @param file the File that needs to be set as Background Image
	 * @return the Background Image for the File 
	 */
	public BackgroundImage backgroundMaker(File file) {
		Image image = null;
		try {
			image = new Image(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		return bgImage;
	}
	

	/***
	 * Reads all the plants from the List of garden plants and places them appropriately in Garden 
	 */
	public void plantReadder(ArrayList<Plant> plants) {
		for(Plant p: plants) {
    		Image plantImage = new Image("img/"+p.name+".png");
			PlantImageView piv = new PlantImageView(p);
			piv.setImage(plantImage);
	    	piv.setPreserveRatio(true);
	    	switch(p.plantSize) {
	    		case "small": piv.setFitHeight(90);
	    		break;
	    		case "medium": piv.setFitHeight(100);
	    		break;
	    		case "large": piv.setFitHeight(130);
	    		break;
	    	}
	    	AnchorPane.setTopAnchor(piv, 0.0);
	    	AnchorPane.setLeftAnchor(piv, 0.0);
	    	ap.getChildren().add(piv);
	    	Tooltip tooltip =  new Tooltip("This is "+p.name+".\n"+"It needs "+p.plantLight+" and "+p.plantSoil+".");
	    	Tooltip.install(piv, tooltip);
	    	piv.setTranslateX(piv.plant.getXLoc());
	    	piv.setTranslateY(piv.plant.getYLoc());
	    	this.plantsInGarden.add(piv);
    	}
	}
	
	/***
	 * Displays the Pop Up on separate Stage for given property(either "light" or "soil") of plant
	 * @param bType the property of plant for which Pop Up will display
	 * @return the Pop Up Stage for the given property of plant
	 */
	public Stage makePopUpForSunSoil(String bType) {
		popUp = new Stage();
		if(bType.equals("light")) {
			popUp.setTitle("Select a Light Type");
		}
		else {
			popUp.setTitle("Select a SoilType");
		}
        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.setHeight(70);
        popUp.setWidth(300);
        popUp.initOwner(this.stage);
		return popUp;
	}
	
	/***
	 * Displays the Pop Up on separate Stage for generating Garden report
	 * @return the Pop Up Stage for generating Garden report
	 */
	public Stage makePopUpForReport() {
		popUp = new Stage();
		popUp.setTitle("Here's Your Custom Generated Report");
		popUp.initModality(Modality.WINDOW_MODAL);
        popUp.setHeight(400);
        popUp.setWidth(500);
        popUp.initOwner(this.stage);
		return popUp;
	}
	
	/***
	 * Creates the FlowPane for Garden report's text and returns it
	 * @param report the Garden report's text that needs to be displayed on Stage
	 * @return the FlowPane with Garden report's text in it
	 */
	public FlowPane makeReportPane(String report, String unhappy) {
		FlowPane pane = new FlowPane();
		pane.setStyle("-fx-background-color: #90EE90;");
		Label labelOne = new Label(report);
		Label labelTwo = new Label(unhappy);
		pane.getChildren().add(labelOne);
		pane.getChildren().add(labelTwo);
		return pane;
	}
	
	/***
	 * Adds the buttons for Soil Pop Up window into the FlowPane and returns the FlowPane
	 * @return the FlowPane with added buttons for Soil Pop Up Stage
	 */
	public FlowPane addButtonsToSoilPopUp() {
		FlowPane pane = new FlowPane();
        Button all = new Button("All");
        all.setUserData("All");
        Button loamy = new Button("Loamy");
        loamy.setUserData("Loamy");
        Button sandy = new Button("Sandy");
        sandy.setUserData("Sandy");
        Button clay = new Button("Clay");
        clay.setUserData("Clay");
        pane.getChildren().add(all);
        pane.getChildren().add(loamy);
        pane.getChildren().add(sandy);
        pane.getChildren().add(clay);
		return pane;
	}
	
	/***
	 * Adds the buttons for Light Pop Up window into the FlowPane and returns the FlowPane
	 * @return the FlowPane with added buttons for Light Pop Up Stage
	 */
	public FlowPane addButtonsToLightPopUp() {
		 FlowPane pane = new FlowPane();
	     Button all = new Button("All");
	     all.setUserData("All");
	     Button full = new Button("Full");
	     full.setUserData("Full");
	     Button partial = new Button("Partial");
	     partial.setUserData("Partial");
	     Button shade = new Button("Shade");
	     shade.setUserData("Shade");
	     pane.getChildren().add(all);
	     pane.getChildren().add(full);
	     pane.getChildren().add(partial);
	     pane.getChildren().add(shade);
	     return pane;
	}

	/***
	 * Initializes the instance variables, sets the Scene, and displays it on Stage 
	 * @param stage the Stage
	 * @param bg the Background Image File that user chooses for their Garden
	 */
	public ViewTwo(Stage stage, File bg, ArrayList<Plant> sbPlants){
		topMenuMaker();
    	gp = new GridPane();
    	gp.setMaxWidth(1);
    	gp.setStyle("-fx-background-color: #ADD8E6");
		buttonMaker(gp);
		plantIVAdder(sbPlants);
    	hbox = new HBox();
    	hbox.getChildren().add(gp);
    	sp = new ScrollPane();
    	sp.setFitToWidth(true);
    	sp.setContent(hbox);
    	ap = new AnchorPane();
    	FlowPaneBG = backgroundMaker(bg);
    	ap.setBackground(new Background(FlowPaneBG));
    	ap.getChildren().add(vbox);
    	bp = new BorderPane();
    	bp.setTop(vbox);
    	bp.setCenter(ap);
    	bp.setLeft(sp);
    	scene = new Scene(bp,WIDTH,HEIGHT);
    	this.stage = stage;
	}
	
	/***
	 * Initializes the instance variables, sets the Scene, and displays it on Stage 
	 * @param stage the Stage
	 * @param garden the Garden that is deserialized
	 */
	public ViewTwo(Stage stage, File bg, ArrayList<Plant> sBPlants, ArrayList<Plant> gPlants) {
		topMenuMaker();
    	gp = new GridPane();
    	gp.setMaxWidth(1);
    	gp.setStyle("-fx-background-color: #ADD8E6");
		buttonMaker(gp);
		plantIVAdder(sBPlants);
    	hbox = new HBox();
    	hbox.getChildren().add(gp);
    	sp = new ScrollPane();
    	sp.setFitToWidth(true);
    	sp.setContent(hbox);
    	ap = new AnchorPane();
    	FlowPaneBG = backgroundMaker(bg);
    	ap.setBackground(new Background(FlowPaneBG));
    	ap.getChildren().add(vbox);
    	plantReadder(gPlants);
    	bp = new BorderPane();
    	bp.setTop(vbox);
    	bp.setCenter(ap);
    	bp.setLeft(sp);
    	scene = new Scene(bp,WIDTH,HEIGHT);
    	this.stage = stage;
	}
	
	public void startShow() {
    	stage.setScene(scene);
    	stage.show();
	}
	
	/***
	 * Returns the most recent Stage for ViewTwo
	 * @return the Stage for ViewTwo
	 */
	public Stage getStage() {
		return stage;
	}
	
	/***
	 * Returns the most recent BorderPane instance
	 * @return the current BorderPane
	 */
	public Parent getBP() {
		return this.bp;
	}

	/***
	 * Returns the most recent Scene for ViewTwo
	 * @return the Scene for ViewTwo
	 */
	public Scene getScene() {
		return scene;
	}
}