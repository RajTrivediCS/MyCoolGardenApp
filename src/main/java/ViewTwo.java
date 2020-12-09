import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
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
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ViewTwo {
	private ArrayList<PlantImageView> sideView = new ArrayList<PlantImageView>();;
	private ArrayList<PlantImageView> plantsInGarden = new ArrayList<PlantImageView>();	
	private ArrayList<PlantImageView> plantsInWasteBasket = new ArrayList<PlantImageView>();
	private Stage popUp;
	private GridPane gp;
	private AnchorPane ap;
	private BorderPane bp;
	private ScrollPane sp;
	private Menu viewMenu;
	private Menu fileMenu;
	private Menu helpMenu;
	private MenuBar topBar;
	private MenuButton sortBy;
	private Button nameButton;
	private Button gSoilButton;
	private Button gLightButton;
	private Button sunButton;
	private Button soilButton;
	private Button newButton;
	private Button loadButton;
	private Button saveButton;
	private Button sizeButton;
	private Button toggleGridButton;
	private Button generateReport;
	private Button how_toButton;
	private BackgroundImage FlowPaneBG;
	private HBox hbox;
	private VBox vbox;
	private Scene scene;
	private ImageView wasteBasket = new ImageView("https://www.freeiconspng.com/thumbs/recycle-bin-icon/recycle-bin-icon-31.png");
	private ImageView tgImgView = new ImageView(new Image("img/ToggleGrid.png"));
	private Stage stage;
	final static private int WIDTH = 1600;
	final static private int HEIGHT = 1000;
	final static private int REPORT_WIDTH = 550;
	final static private int REPORT_HEIGHT = 450;
	final static private int HELP_HEIGHT = 750;
	final static private int HELP_WIDTH = 650;
	final static private int SS_HEIGHT = 70;
	final static private int SS_WIDTH = 300;
	final static private int IMAGEVIEW_SIZE = 130;
	final static private int WASTEBASKET_SIZE = 90;
	final static private int GRID_WIDTH = 900;
	final static private int GRID_HEIGHT = 900;

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
		fileMenu = new Menu("File");
		viewMenu = new Menu("Garden Options");
		helpMenu = new Menu("Help");
		
		newButton = new Button("New");
		CustomMenuItem newItem = new CustomMenuItem(newButton);
		fileMenu.getItems().add(newItem);

		saveButton = new Button("Save");
		CustomMenuItem saveItem = new CustomMenuItem(saveButton);
		fileMenu.getItems().add(saveItem);
		
		loadButton = new Button("Load");
		CustomMenuItem loadItem = new CustomMenuItem(loadButton);
		fileMenu.getItems().add(loadItem);
		
		gLightButton = new Button("Choose Garden Light");
		CustomMenuItem gLightItem = new CustomMenuItem(gLightButton);
		viewMenu.getItems().add(gLightItem);
		
		gSoilButton = new Button("Choose Garden Soil");
		CustomMenuItem gSoilItem = new CustomMenuItem(gSoilButton);
		viewMenu.getItems().add(gSoilItem);
		
		
		toggleGridButton = new Button("Toggle Grid");
		CustomMenuItem togglegridItem = new CustomMenuItem(toggleGridButton);
		viewMenu.getItems().add(togglegridItem);
		
		Button togglebackgroundButton = new Button("Toggle Background");
		CustomMenuItem togglebackgroundItem = new CustomMenuItem(togglebackgroundButton);
		viewMenu.getItems().add(togglebackgroundItem);
		
		generateReport = new Button("Generate Garden Report");
		CustomMenuItem generateReportItem = new CustomMenuItem(generateReport);
		viewMenu.getItems().add(generateReportItem);
		
		how_toButton = new Button("How-to");
		CustomMenuItem how_toItem = new CustomMenuItem(how_toButton);
		helpMenu.getItems().add(how_toItem);
		
		topBar = new MenuBar();
		
		topBar.getMenus().add(fileMenu);
		topBar.getMenus().add(viewMenu);
		topBar.getMenus().add(helpMenu);
		vbox = new VBox(topBar);
		
	}
	
	/***
	 * Adds the Images for the given List of plants in the SideView with hover-over implementation
	 * @param plants the List of plants to add Images for
	 */
	public void plantIVAdder(ArrayList<Plant> plants) {
		int i=0;
		for(Plant p : plants) {
			Image im1 = new Image("img/"+p.getName()+".png");
			PlantImageView piv = new PlantImageView(p);
			piv.setImage(im1);
	    	piv.setPreserveRatio(true);
	    	piv.setFitHeight(IMAGEVIEW_SIZE);
	    	Tooltip tooltip =  new Tooltip("This is "+p.getName()+".\n"+"It needs "+p.getPlantLight()+" and "+p.getPlantSoil()+". \nIt is roughly " + p.getPlantSize() + " feet in diameter.");
	    	Tooltip.install(piv, tooltip);
	    	piv.setPaneLoc("grid");
			sideView.add(piv);
	    	gp.add(piv, 0, i+1);
	    	i++;
	    }
		wasteBasket.setFitHeight(WASTEBASKET_SIZE);
		wasteBasket.setFitWidth(WASTEBASKET_SIZE);
		gp.add(wasteBasket, 0, i+1);
		
	}
	/**
	 * formula for making the imageview size dynamic based on a plants diameter
	 * @param plantWidth
	 * @return
	 */
	
	public int setHeightFormula(double plantWidth, int scale) {
		int height;
		height = (int)plantWidth * 4 + 70; 
		height = (int) (height * 250000 / scale);
		return height;
	}
	
	/***
	 * Sorts List of plant image view by name of plant
	 */
	public void nameSort() {
		Collections.sort(sideView,new Comparator<PlantImageView>(){
			@Override
			public int compare(PlantImageView p1, PlantImageView p2) {
				return p1.getPlant().getName().compareTo(p2.getPlant().getName());
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
				int i = p1.getPlant().getPlantLight().compareTo(p2.getPlant().getPlantLight());
				if(i==0) {
					return p1.getPlant().getName().compareTo(p2.getPlant().getName());
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
				int i = p1.getPlant().getPlantSoil().compareTo(p2.getPlant().getPlantSoil());
				if(i==0) {
					return p1.getPlant().getName().compareTo(p2.getPlant().getName());
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
				int size1 = (int) (Double.parseDouble(p1.getPlant().getPlantSize()) * 10);
				int size2 = (int) (Double.parseDouble(p2.getPlant().getPlantSize()) * 10);
				int i = size1 - size2;
				if(i==0) {
					return p1.getPlant().getName().compareTo(p2.getPlant().getName());
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
	public void plantReadder(ArrayList<Plant> plants, int scale) {
		for(Plant p: plants) {
    		Image plantImage = new Image("img/"+p.getName()+".png");
			PlantImageView piv = new PlantImageView(p);
			piv.setImage(plantImage);
	    	piv.setPreserveRatio(true);
	    	int size = setHeightFormula(Double.parseDouble(piv.getPlant().getPlantSize()), scale);
			piv.setFitHeight(size);
	    	AnchorPane.setTopAnchor(piv, 0.0);
	    	AnchorPane.setLeftAnchor(piv, 0.0);
	    	ap.getChildren().add(piv);
	    	Tooltip tooltip =  new Tooltip("This is "+p.getName()+".\n"+"It needs "+p.getPlantLight()+" and "+p.getPlantSoil()+". \nIt is roughly " + p.getPlantSize() + " feet in diameter.");
	    	Tooltip.install(piv, tooltip);
	    	piv.setTranslateX(piv.getPlant().getXLoc());
	    	piv.setTranslateY(piv.getPlant().getYLoc());
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
        popUp.setHeight(SS_HEIGHT);
        popUp.setWidth(SS_WIDTH);
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
        popUp.setHeight(REPORT_HEIGHT);
        popUp.setWidth(REPORT_WIDTH);
        popUp.initOwner(this.stage);
		return popUp;
	}
	
	public Stage makePopUpForHelp() {
		popUp = new Stage();
		popUp.setTitle("How-to");
		popUp.initModality(Modality.WINDOW_MODAL);
		popUp.setHeight(HELP_HEIGHT);
		popUp.setWidth(HELP_WIDTH);
		popUp.initOwner(this.stage);
		return popUp;
	}
	
	public FlowPane makeHelpPane(String helpText) {
		// TODO Auto-generated method stub
		FlowPane pane = new FlowPane();
		pane.setStyle("-fx-background-color: #90EE90;");
		Label labelOne = new Label(helpText);
		pane.getChildren().add(labelOne);
		return pane;
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
	public ViewTwo(Stage stage, File bg, ArrayList<Plant> sbPlants, int scale){
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
    	if(bg != null) {
        	FlowPaneBG = backgroundMaker(bg);
        	ap.setBackground(new Background(FlowPaneBG));
    	}
    	else {
    		ap.setStyle("-fx-background-color: #90EE90");
    	}
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
	public ViewTwo(Stage stage, File bg, ArrayList<Plant> sBPlants, ArrayList<Plant> gPlants, int scale) {
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
    	plantReadder(gPlants, scale);
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
	
	public void addGridImage() {
		tgImgView.setFitHeight(GRID_HEIGHT * 2);
		tgImgView.setFitWidth(GRID_WIDTH * 2);
		tgImgView.setPreserveRatio(true);
		ap.getChildren().add(tgImgView);
		tgImgView.toBack();
		startShow();
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


	public ArrayList<PlantImageView> getSideView() {
		return sideView;
	}


	public MenuButton getSortBy() {
		return sortBy;
	}


	public ArrayList<PlantImageView> getPlantsInGarden() {
		return plantsInGarden;
	}


	public Button getNewButton() {
		return newButton;
	}


	public Button getLoadButton() {
		return loadButton;
	}


	public Button getSaveButton() {
		return saveButton;
	}


	public Button getGSoilButton() {
		return gSoilButton;
	}


	public Button getGLightButton() {
		return gLightButton;
	}


	public Button getGenerateReport() {
		return generateReport;
	}


	public AnchorPane getAP() {
		return ap;
	}


	public GridPane getGP() {
		return gp;
	}


	public ArrayList<PlantImageView> getPlantsInWasteBasket() {
		return plantsInWasteBasket;
	}


	public void setPopUp(Stage makePopUpForSunSoil) {
		popUp = makePopUpForSunSoil;
	}


	public Stage getPopUp() {
		return popUp;
	}


	public ImageView getWasteBasketIV() {
		return wasteBasket;
	}


	public Button getNameButton() {
		return nameButton;
	}


	public Button getSunButton() {
		return sunButton;
	}


	public Button getSoilButton() {
		return soilButton;
	}


	public Button getSizeButton() {
		return sizeButton;
	}	
	
	public Button getToggleGridButton() {
		return toggleGridButton;
	}
	
	public ImageView gettgImgView() {
		return tgImgView;
	}


	public ImageView getTG() {
		return tgImgView;
	}


	public Button getHow_To() {
		// TODO Auto-generated method stub
		return how_toButton;
	}

}