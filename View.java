import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class View {
	double xMinLeftBar = 200, yMinLeftBar = 0;
	double xMaxLeftBar = 300, yMaxLeftBar = 900;
	double xMinWorkSpace = 300, yMinWorkSpace = 0;
	double xMaxWorkSpace = 1300, yMaxWorkSpace = 900;
	double xMinWasteBasket;
	double yMinWasteBasket;
	double xMaxWasteBasket;
	double yMaxWasteBasket;
	
	ArrayList<PlantImageView> sideView = new ArrayList<PlantImageView>();;
	ArrayList<PlantImageView> plantsInGarden = new ArrayList<PlantImageView>();
	
	TilePane tp;
	FlowPane fp;
	BorderPane bp;
	ScrollPane sp;
	MenuButton sortBy;
	HBox hbox;
	//Button newGardenButton; FIXME: Move this to a different Scene/View
	//Button loadGardenButton;
	public void plantIVAdder(ArrayList<Plant> plants) {
		int i=0;
		for(Plant p : plants) {
			Image im1 = new Image(getClass().getResourceAsStream("img/"+i+".png"));
			PlantImageView piv = new PlantImageView(p);
			piv.setImage(im1); //write function to change to a plant later
	    	piv.setPreserveRatio(true);
	    	piv.setFitHeight(100);
	    	Tooltip tooltip =  new Tooltip("This is "+p.name);
	    	Tooltip.install(piv, tooltip);
	    	piv.setPaneLoc("tile");
			sideView.add(piv);
	    	tp.getChildren().add(piv);
	    	i++;
	    }
	}
	/**
	 * Simple constructor that sets initial imageview and controller.
	 */

	public View(ArrayList<Plant> plants){		
		int i = 0;
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
		tp.getChildren().add(sortBy);
    	
		plantIVAdder(plants);
		
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
    	bp.setCenter(fp);
    	bp.setLeft(sp);
	}



	public Parent getBP() {
		return this.bp;
	}



	public TilePane getTP() {
		return this.tp;
	}
}

