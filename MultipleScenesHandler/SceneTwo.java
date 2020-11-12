package MultipleScenesHandler;

import java.util.ArrayList; 
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;


public class SceneTwo {
	double xMinLeftBar = 200, yMinLeftBar = 0;
	double xMaxLeftBar = 300, yMaxLeftBar = 900;
	double xMinWorkSpace = 300, yMinWorkSpace = 0;
	double xMaxWorkSpace = 1300, yMaxWorkSpace = 900;
	ArrayList<PlantImageView> sideView;
	ArrayList<PlantImageView> plantsInGarden;
	Scene scene;
	TilePane tp;
	FlowPane fp;
	BorderPane bp;
	ScrollPane sp;
	MenuButton sortBy;
	HBox hbox;
	ArrayList<Plant> plants;

	/**
	 * Simple constructor that sets initial imageview and controller.
	 */

	public SceneTwo(Stage stage){
		plants = new SceneModel().hotBarPlants;
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
		tp.getChildren().add(sortBy);
    	for(Plant p : plants) {
    		Image im1 = new Image("img/"+i+".png");
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
        
    	bp = new BorderPane();
    	bp.setCenter(fp);
    	bp.setLeft(sp);
    	scene = new Scene(bp,800,600);
    	stage.setScene(scene);
    	stage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
}

