import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
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
	
	ArrayList<PlantImageView> sideView;
	
	TilePane tp;
	FlowPane fp;
	BorderPane bp;
	ScrollPane sp;
	MenuButton sortBy;
	HBox hbox;



	/**
	 * Simple constructor that sets initial imageview and controller.
	 */

	public View(ArrayList<Plant> plants){		
		Image im1 = new Image(getClass().getResourceAsStream("commonMilkweed.png"));
		sideView = new ArrayList<PlantImageView>();
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
    		PlantImageView piv = new PlantImageView(p);
    		piv.setImage(im1); //write function to change to a plant later
        	piv.setPreserveRatio(true);
        	piv.setFitHeight(100);
        	Tooltip tooltip =  new Tooltip("This is "+p.name);
        	Tooltip.install(piv, tooltip);
        	piv.setPaneLoc("tile");
    		sideView.add(piv);
        	tp.getChildren().add(piv);
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
	}
}

