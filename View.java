import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.Map;

import MultipleScenesHandler.SceneName;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class View {
	Controller controller;
	
	ArrayList<PlantImageView> sideView = new ArrayList<PlantImageView>();;
	ArrayList<PlantImageView> plantsInGarden = new ArrayList<PlantImageView>();
	
	TilePane tp;
	GridPane gp;
	FlowPane fp;
	BorderPane bp;
	BorderPane wp;
	ScrollPane sp;
	Button nameButton;
	Button newButton;
	Button loadButton;
	Button saveButton;
	Button soilButton;
	Button sunButton;
	HBox hbox;
	VBox vbox;
	Map<SceneName,Scene> sceneMap;

	//Button newGardenButton; FIXME: Move this to a different Scene/View
	//Button loadGardenButton;
	public void buttonMaker(GridPane grid) {		
    	MenuButton sortBy = new MenuButton("Sort by");
    	
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
	
	public void plantIVAdder(ArrayList<Plant> plants) {
		int i=0;
		for(Plant p : plants) {
			Image im1 = new Image(getClass().getResourceAsStream("img/"+p.name+".png"));
			PlantImageView piv = new PlantImageView(p);
			piv.setImage(im1); //write function to change to a plant later
	    	piv.setPreserveRatio(true);
	    	piv.setFitHeight(100);
	    	Tooltip tooltip =  new Tooltip("This is "+p.name+".\n"+"It needs "+p.plantLight+" and "+p.plantSoil+".");
	    	Tooltip.install(piv, tooltip);
	    	piv.setPaneLoc("grid");
			sideView.add(piv);
	    	gp.add(piv, 0, i+1);
	    	i++;
	    }
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
		if (sortMode.equals("name")) {
			nameSort();
		}
		else if (sortMode.equals("sun")) {
			sunSort();
		}
		else if (sortMode.equals("soil")) {
			soilSort();
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
	
	/*public void openWindow() {
		Stage newWindow = new Stage();
		sceneMap = new SceneContainer(newWindow).getSceneMap();
		controller.start(newWindow);
	}*/
	
	public void saveGarden() {
		Stage fileDirectory = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Save Location");
		fileChooser.showOpenDialog(fileDirectory);
	}
	
	public void loadGarden() {
		Stage fileDirectory = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.showOpenDialog(fileDirectory);
	}
	
	/**
	 * Simple constructor that sets initial imageview and controller.
	 */

	public View(ArrayList<Plant> plants){	
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
    	fp.setStyle("-fx-background-color: #BFFF00");
    	
    	/*FIXME: THIS NEEDS TO GO SOMEWHERE ELSE. NOT PART OF THIS SCENE/VIEW
    	newGardenButton = new Button(" (+) New Garden");
    	newGardenButton.setTranslateX(70);
    	newGardenButton.setTranslateY(40);
    	loadGardenButton = new Button("Load Garden");
    	loadGardenButton.setTranslateX(90);
    	loadGardenButton.setTranslateY(40);
    	newGardenButton.setPrefHeight(50);
    	newGardenButton.setPrefWidth(150);
    	loadGardenButton.setPrefHeight(50);
    	loadGardenButton.setPrefWidth(150);
    	fp.getChildren().add(newGardenButton);
    	fp.getChildren().add(loadGardenButton);
        */
    	
    	bp = new BorderPane();
    	bp.setTop(vbox);
    	bp.setCenter(fp);
    	bp.setLeft(sp);
    	
    	/*wp = new BorderPane();
    	wp.setTop(vbox);
    	wp.setCenter(fp);
    	wp.setLeft(sp);*/
	}



	public Parent getBP() {
		return this.bp;
		
	}
	
	/*public Parent getWP() {
		return this.wp;
		
	}*/



	public TilePane getTP() {
		return this.tp;
	}
}

