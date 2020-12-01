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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ViewTwo {
	ModelTwo model = new ModelTwo();
	ArrayList<PlantImageView> sideView = new ArrayList<PlantImageView>();;
	ArrayList<PlantImageView> plantsInGarden = new ArrayList<PlantImageView>();	
	ArrayList<PlantImageView> plantsInWasteBasket = new ArrayList<PlantImageView>();
	TilePane tp;
	GridPane gp;
	FlowPane fp;
	BorderPane bp;
	ScrollPane sp;
	Menu viewMenu;
	Menu fileMenu;
	Menu undoMenu;
	Menu redoMenu;
	MenuItem undoItem;
	MenuItem redoItem;
	MenuBar menuBar;
	MenuButton sortBy;
	Button nameButton;
	Button soilButton;
	Button sunButton;
	Button newButton;
	Button loadButton;
	Button saveButton;
	BackgroundImage FlowPaneBG;
	HBox hbox;
	VBox vbox;
	Scene scene;
	ArrayList<Plant> plants;
	Image undoImage = new Image("img/undoImage.png");
	Image redoImage = new Image("img/redoImage.png");
	ImageView wasteBasket = new ImageView("https://www.freeiconspng.com/thumbs/recycle-bin-icon/recycle-bin-icon-31.png");
	ImageView undoImgView;
	ImageView redoImgView;
	ControllerTwo controllerTwo;
	ControllerTwo controller2;
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
		
		sortBy.setOnMouseClicked(e->controllerTwo.sortButtonHandler());
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
		
		
		Button togglegridButton = new Button("Toggle Grid");
		CustomMenuItem togglegridItem = new CustomMenuItem(togglegridButton);
		viewMenu.getItems().add(togglegridItem);
		
		Button togglebackgroundButton = new Button("Toggle Background");
		CustomMenuItem togglebackgroundItem = new CustomMenuItem(togglebackgroundButton);
		viewMenu.getItems().add(togglebackgroundItem);
		
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
	    	controllerTwo.setHandlerForPress(piv);
	    	i++;
	    }
		wasteBasket.setFitHeight(90);
		wasteBasket.setFitWidth(90);
		gp.add(wasteBasket, 0, i+1);
		
	}
	
	/***
	 * Sorts List of plant image view by name type
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
	 * Sorts List of plant image view by sun type
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
	 * Sorts List of plant image view by soil type
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
	 * Uses sort mode to sort the List of plant image view
	 * @param sortMode the mode(either "name", "sun", or "soil") for sorting all Plants
	 */
	//
	public void sideViewSortHelper(String sortMode) { // convert to enum in the future
		switch(sortMode) {
			case "name": nameSort();
			break;
			case "sun" : sunSort();
			break;
			case "soil": soilSort();
			break;
		}
	}
	
	/***
	 * Sorts the ImageViews in the SideBar based on the given sort mode
	 * @param sortMode the mode(either "name", "sun", or "soil") for sorting all Plants 
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
	 * Initializes the instance variables, sets the Scene, and displays it on Stage 
	 * @param stage the Stage
	 * @param bg the Background Image File that user chooses for their Garden
	 */
	public ViewTwo(Stage stage, File bg){
		controllerTwo = new ControllerTwo();
		controllerTwo.setViewTwo(this);
		controllerTwo.setModelTwo(this.model);
		plants = model.getHotBarPlants();
		topMenuMaker();
    	gp = new GridPane();
    	gp.setMaxWidth(1);
    	gp.setStyle("-fx-background-color: #ADD8E6");
		buttonMaker(gp);
		plantIVAdder(plants);
    	hbox = new HBox();
    	hbox.getChildren().add(gp);
    	sp = new ScrollPane();
    	sp.setFitToWidth(true);
    	sp.setContent(hbox);
    	fp = new FlowPane();
    	FlowPaneBG = backgroundMaker(bg);
    	fp.setBackground(new Background(FlowPaneBG));
    	fp.getChildren().add(vbox);
    	bp = new BorderPane();
    	bp.setTop(vbox);
    	bp.setCenter(fp);
    	bp.setLeft(sp);
    	model.garden.setBg(bg);
    	scene = new Scene(bp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	this.stage = stage;
    	newButton.setOnAction(e-> controllerTwo.handleNewButtonPress(e));
    	loadButton.setOnAction(e-> controllerTwo.handleLoadButtonPress(e));
    	saveButton.setOnAction(e->controllerTwo.handleSaveButton(stage));
    	stage.show();
	}
	
	/***
	 * Initializes the instance variables, sets the Scene, and displays it on Stage 
	 * @param stage the Stage
	 * @param garden the Garden that is deserialized
	 */
	public ViewTwo(Stage stage, Garden garden) {
		controllerTwo = new ControllerTwo();
		controllerTwo.setViewTwo(this);
		controllerTwo.setModelTwo(this.model);
		this.model.garden = garden;
		plants = model.getHotBarPlants();
		topMenuMaker();
    	gp = new GridPane();
    	gp.setMaxWidth(1);
    	gp.setStyle("-fx-background-color: #ADD8E6");
		buttonMaker(gp);
		plantIVAdder(plants);
    	hbox = new HBox();
    	hbox.getChildren().add(gp);
    	sp = new ScrollPane();
    	sp.setFitToWidth(true);
    	sp.setContent(hbox);
    	fp = new FlowPane();
    	FlowPaneBG = backgroundMaker(garden.getBg());
    	fp.setBackground(new Background(FlowPaneBG));
    	fp.getChildren().add(vbox);
    	for(Plant p: model.garden.gardensPlants) {
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
	    	controllerTwo.setHandlerForDrag(piv);
	    	controllerTwo.setHandlerDeletePlant(piv);
	    	fp.getChildren().add(piv);
	    	piv.setTranslateX(piv.plant.getXLoc());
	    	piv.setTranslateY(piv.plant.getYLoc());
	    	this.plantsInGarden.add(piv);
    	}
		for(Plant p : model.garden.gardensPlants) {
			if(p.id > controllerTwo.identifier) {
				controllerTwo.identifier = p.id;
			}
		}
    	bp = new BorderPane();
    	bp.setTop(vbox);
    	bp.setCenter(fp);
    	bp.setLeft(sp);
    	scene = new Scene(bp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	this.stage = stage;
    	newButton.setOnAction(e-> controllerTwo.handleNewButtonPress(e));
    	loadButton.setOnAction(e-> controllerTwo.handleLoadButtonPress(e));
    	saveButton.setOnAction(e->controllerTwo.handleSaveButton(stage));
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
	 * Returns the most recent TilePane instance
	 * @return the current TilePane
	 */
	public TilePane getTP() {
		return this.tp;
	}
	
	/***
	 * Returns the most recent Scene for ViewTwo
	 * @return the Scene for ViewTwo
	 */
	public Scene getScene() {
		return scene;
	}
}