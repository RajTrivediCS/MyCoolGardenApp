package Application;

import InitialPackage.*;
import java.util.ArrayList; 
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ViewTwo extends View{
	double xMinLeftBar = 200, yMinLeftBar = 0;
	double xMaxLeftBar = 300, yMaxLeftBar = 900;
	double xMinWorkSpace = 300, yMinWorkSpace = 0;
	double xMaxWorkSpace = 1300, yMaxWorkSpace = 900;
	ArrayList<PlantImageView> sideView;
	ArrayList<PlantImageView> plantsInGarden;
//	Menu fileMenu;
//	Menu viewMenu;
	Menu undoMenu;
	Menu redoMenu;
/*	MenuItem fileMenuItem1;
	MenuItem fileMenuItem2;
	MenuItem fileMenuItem3;
	MenuItem viewMenuItem1;
	MenuItem viewMenuItem2;
	MenuItem viewMenuItem3;
*/	MenuItem undoItem;
	MenuItem redoItem;
	MenuBar menuBar;
	VBox vBox;
	Scene scene;
	TilePane tp;
	FlowPane fp;
	BorderPane bp;
	ScrollPane sp;
	MenuButton sortBy;
	HBox hbox;
	ArrayList<Plant> plants;
	Image undoImage = new Image("img/undoImage.png");
	Image redoImage = new Image("img/redoImage.png");
	ImageView undoImgView;
	ImageView redoImgView;
	Model model = new Model();
	
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	final static int MENU_BAR_WIDTH = 1500;
	final static int MENU_BAR_HEIGHT = 32;
	
	/**
	 * Simple constructor that sets initial imageview and controller.
	 */
	public ViewTwo(Stage stage){

		// Undo and Redo Image View
		undoImgView = new ImageView(undoImage);
		redoImgView = new ImageView(redoImage);
		undoImgView.setPreserveRatio(true);
		redoImgView.setPreserveRatio(true);
		undoImgView.setFitWidth(100);
		undoImgView.setFitHeight(20);
		redoImgView.setFitWidth(100);
		redoImgView.setFitHeight(20);

		fp = new FlowPane();

/*		// Creates items to be added for a Menu
		fileMenuItem1 = new MenuItem("File Menu Item 1");
		fileMenuItem2 = new MenuItem("File Menu Item 2");
		fileMenuItem3 = new MenuItem("File Menu Item 3");
		viewMenuItem1 = new MenuItem("View Menu Item 1");
		viewMenuItem2 = new MenuItem("View Menu Item 2");
		viewMenuItem3 = new MenuItem("View Menu Item 3");
*/		undoItem = new MenuItem("Undo Item");
		redoItem = new MenuItem("Redo Item");

		// Creates a Menu 
//		fileMenu = new Menu("File");
//		viewMenu = new Menu("View");
		undoMenu = new Menu("",undoImgView);
		redoMenu = new Menu("",redoImgView);

		// Adds items for specific Menu
/*		fileMenu.getItems().add(fileMenuItem1);
		fileMenu.getItems().add(fileMenuItem2);
		fileMenu.getItems().add(fileMenuItem3);
		viewMenu.getItems().add(viewMenuItem1);
		viewMenu.getItems().add(viewMenuItem2);
		viewMenu.getItems().add(viewMenuItem3);
*/		undoMenu.getItems().add(undoItem);
	    redoMenu.getItems().add(redoItem);

		// Creates a Menu Bar
		menuBar = new MenuBar();
//		menuBar.getMenus().add(fileMenu);
//		menuBar.getMenus().add(viewMenu);
		menuBar.getMenus().add(undoMenu);
		menuBar.getMenus().add(redoMenu);



		menuBar.setPrefWidth(MENU_BAR_WIDTH);
		menuBar.setPrefHeight(MENU_BAR_HEIGHT);

		// Creates a VBox
		vBox = new VBox(menuBar);
		vBox.setTranslateY(0);

		plants = new Model().getHotBarPlants();
		int i = 0;
		sideView = new ArrayList<PlantImageView>();
		plantsInGarden = new ArrayList<PlantImageView>();
    	tp = new TilePane();
    	tp.setMaxWidth(1);
		sortBy = new MenuButton("Sort by");
		Button colorButton = new Button("Color");
		CustomMenuItem colorItem = new CustomMenuItem(colorButton);
		sortBy.getItems().add(colorItem);
		colorItem.setHideOnClick(false);
		
		Button flowersButton = new Button("Flowers");
		CustomMenuItem flowersItem = new CustomMenuItem(flowersButton);
		sortBy.getItems().add(flowersItem);
		flowersItem.setHideOnClick(false);
		sortBy.setTranslateY(30);
		tp.getChildren().add(sortBy);
    	for(Plant p : plants) {
    		Image im1 = new Image("img/"+i+".png");
    		PlantImageView piv = new PlantImageView(p);
    		piv.setImage(im1); //write function to change to a plant later
        	piv.setPreserveRatio(true);
        	piv.setFitHeight(100);
        	Tooltip tooltip =  new Tooltip("This is "+ p.toString());
        	Tooltip.install(piv, tooltip);
        	piv.setPaneLoc("tile");
    		sideView.add(piv);
        	tp.getChildren().add(piv);
        	i++;
    	}
    	tp.setPrefColumns(1);
    	tp.setStyle("-fx-background-color: #ADD8E6");
    	tp.setMaxWidth(1);
    	hbox = new HBox();
    	hbox.getChildren().add(tp);
    	sp = new ScrollPane();
    	sp.setFitToWidth(true);
    	sp.setContent(hbox);	
    	fp = new FlowPane();
    	fp.setStyle("-fx-background-color: #BFFF00");
    	fp.getChildren().add(vBox);

    	bp = new BorderPane();
    	bp.setCenter(fp);
    	bp.setLeft(sp);
    	scene = new Scene(bp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	stage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
}

