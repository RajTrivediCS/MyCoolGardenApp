import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class View {
	double xMinLeftBar, yMinLeftBar;
	double xMaxLeftBar, yMaxLeftBar;
	double xMinWorkSpace, yMinWorkSpace;
	double xMaxWorkSpace, yMaxWorkSpace;
	double xMinWasteBasket;
	double yMinWasteBasket;
	double xMaxWasteBasket;
	double yMaxWasteBasket;
	
	ArrayList<PlantImageView> sideView;
	
	TilePane tp;
	FlowPane fp;
	BorderPane bp;
	

	/**
	 * Simple constructor that sets initial imageview and controller.
	 */
	public View(ArrayList<Plant> plants){
		Image im1 = new Image(getClass().getResourceAsStream("commonMilkweed.png"));
		sideView = new ArrayList<PlantImageView>();
    	tp = new TilePane();
    	for(Plant p : plants) {
    		PlantImageView piv = new PlantImageView(p);
    		piv.setImage(im1); //write function to change to a plant later
        	piv.setPreserveRatio(true);
        	piv.setFitHeight(100);
        	piv.setPaneLoc("tile");
    		sideView.add(piv);
        	tp.getChildren().add(piv);
    	}

    	tp.setPrefColumns(1);
    	tp.setStyle("-fx-background-color: #ADD8E6");
    	fp = new FlowPane();
    	fp.setStyle("-fx-background-color: #BFFF00");
    	bp = new BorderPane();
    	bp.setCenter(fp);
    	bp.setLeft(tp);
    	
	}
}

