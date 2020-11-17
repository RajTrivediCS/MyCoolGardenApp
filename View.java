import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class View {
	ArrayList<PlantImageView> sideView = new ArrayList<PlantImageView>();;
	ArrayList<PlantImageView> plantsInGarden = new ArrayList<PlantImageView>();
	
	TilePane tp;
	GridPane gp;
	FlowPane fp;
	BorderPane bp;
	ScrollPane sp;
	MenuButton sortBy;
	Button nameButton;
	Button soilButton;
	Button sunButton;
	HBox hbox;
	//Button newGardenButton; FIXME: Move this to a different Scene/View
	//Button loadGardenButton;
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
		grid.getChildren().add(sortBy);
	}
	
	public void plantIVAdder(ArrayList<Plant> plants) {
		int i=0;
		for(Plant p : plants) {
			Image im1 = new Image(getClass().getResourceAsStream("img/"+i+".png"));
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
	
	public void sideViewSortHelper(String sortMode) { // convert to enum in the future
		if (sortMode.equals("name")) {
			Collections.sort(sideView,new Comparator<PlantImageView>(){
				@Override
				public int compare(PlantImageView p1, PlantImageView p2) {
					return p1.plant.name.compareTo(p2.plant.name);
				}
				
			});
		}
		else if (sortMode.equals("sun")) {
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
		else if (sortMode.equals("soil")) {
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
	}
	
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
    		System.out.println(p);
    		i++;
    	}
	}
	
	
	/**
	 * Simple constructor that sets initial imageview and controller.
	 */

	public View(ArrayList<Plant> plants){	
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

