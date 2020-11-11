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

	PlantImageView iv1;
	TilePane tp;
	FlowPane fp;
	BorderPane bp;
	
	public PlantImageView getIv1() {
		return iv1;
	}

	/**
	 * Simple constructor that sets initial imageview and controller.
	 */
	public View(){
		Image im1 = new Image(getClass().getResourceAsStream("commonMilkweed.png"));
    	iv1 = new PlantImageView();		
    	iv1.setPaneLoc("tile");
    	tp = new TilePane(iv1);
    	tp.setPrefColumns(1);
    	tp.setStyle("-fx-background-color: #ADD8E6");
    	fp = new FlowPane();
    	fp.setStyle("-fx-background-color: #BFFF00");
    	bp = new BorderPane();
    	bp.setCenter(fp);
    	bp.setLeft(tp);
    	iv1.setImage(im1);
    	iv1.setPreserveRatio(true);
    	iv1.setFitHeight(100);
	}
}

