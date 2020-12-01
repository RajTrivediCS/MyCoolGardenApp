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


public class ViewTwo {
	ModelTwo model = new ModelTwo();
	ArrayList<PlantImageView> sideView = new ArrayList<PlantImageView>();;
	ArrayList<PlantImageView> plantsInGarden = new ArrayList<PlantImageView>();	
	ArrayList<PlantImageView> plantsInWasteBasket = new ArrayList<PlantImageView>();
	GridPane gp;
	FlowPane fp;
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
	ControllerTwo controllerTwo;
	ControllerTwo controller2;
	Stage stage;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;

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
		
		sortBy.setOnMouseClicked(e->controllerTwo.sortButtonHandler(this));
		grid.getChildren().add(sortBy);
	}
	

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
	    	controllerTwo.setHandlerForPress(this, piv);
	    	i++;
	    }
		wasteBasket.setFitHeight(90);
		wasteBasket.setFitWidth(90);
		gp.add(wasteBasket, 0, i+1);
		
	}
	//sort by name type
	public void nameSort() {
		Collections.sort(sideView,new Comparator<PlantImageView>(){
			@Override
			public int compare(PlantImageView p1, PlantImageView p2) {
				return p1.plant.name.compareTo(p2.plant.name);
			}
		});
	}
	//sort by sun type
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
	//sort by soil type
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
	
	//uses sortmode to sort the sideview plant image views ArrayList
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
	
	//sorts the ImageViews in the sideBar based on the sort mode
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
	
	public void setOnActionAdder() {
    	newButton.setOnAction(e-> controllerTwo.handleNewButtonPress(e));
    	saveButton.setOnAction(e->controllerTwo.handleSaveButton(stage));
    	gSoilButton.setOnAction(e->controllerTwo.handleGSoilButton(e));
    	gLightButton.setOnAction(e->controllerTwo.handleLightButton(e));
    	generateReport.setOnAction(e->controllerTwo.handleGenerateReport(e));
	}
	
	public void plantReadder() {
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
	    	controllerTwo.setHandlerForDrag(piv, this);
	    	controllerTwo.setHandlerDeletePlant(piv, this);
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
	}
	
	public Stage makePopUpForSunSoil(String bType) {
		Stage popUp = new Stage();
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
	
	public Stage makePopUpForReport() {
		Stage popUp = new Stage();
		popUp.setTitle("Here's Your Custom Generated Report");
		popUp.initModality(Modality.WINDOW_MODAL);
        popUp.setHeight(400);
        popUp.setWidth(500);
        popUp.initOwner(this.stage);
		return popUp;
	}
	
	public FlowPane makeReportPane(String report) {
		FlowPane pane = new FlowPane();
		Label label = new Label(report);
		pane.getChildren().add(label);
		return pane;
	}
	
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

	public ViewTwo(Stage stage, File bg){
		controllerTwo = new ControllerTwo();
		controllerTwo.setViewTwo(this);
		controllerTwo.setModelTwo(this.model);
		ArrayList<Plant> plants = model.getHotBarPlants();
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
    	setOnActionAdder();
    	stage.show();
	}
	
	/***
	 * Overloaded constructor for Deserializing Garden object and set it on Stage
	 * @param stage Stage
	 * @param garden Garden to be deserialize
	 */
	public ViewTwo(Stage stage, Garden garden) {
		controllerTwo = new ControllerTwo();
		controllerTwo.setViewTwo(this);
		controllerTwo.setModelTwo(this.model);
		this.model.garden = garden;
		ArrayList<Plant> plants = model.getHotBarPlants();
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
    	plantReadder();
    	bp = new BorderPane();
    	bp.setTop(vbox);
    	bp.setCenter(fp);
    	bp.setLeft(sp);
    	scene = new Scene(bp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	this.stage = stage;
    	setOnActionAdder();
    	stage.show();
	}
	
	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public Parent getBP() {
		return this.bp;
	}
	
	public Scene getScene() {
		return scene;
	}
}