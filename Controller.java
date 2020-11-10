import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Controller extends Application {
	//Model model = new Model();
	View view = new View();
	
	//handleAddingToGarden
	//handleMovingInGarden
	//handleDeletionFromGarden
	public void drag(MouseEvent event, PlantImageView v) {
		Node n = (Node)event.getSource();
		n.setTranslateX(n.getTranslateX() + event.getX());
		n.setTranslateY(n.getTranslateY() + event.getY());
		v.setPaneLoc("flow");
		System.out.println(event.getSceneX() + ", " + event.getSceneY());
	}	
	
	public void enter(MouseEvent event, PlantImageView v) {
		if(v.getPaneLoc().equals("tile")) {
			view.fp.getChildren().add(v);
			handleReplaceImgView(view.tp);
		}
	}
	
	public void setHandlerForDrag(PlantImageView iv1) {
		iv1.setOnMouseDragged(event -> drag(event, iv1));		
	}
	
	public void setHandlerForPress(PlantImageView v) {
		v.setOnMousePressed(event->enter(event, v));
	}
	
	public void handleReplaceImgView(TilePane tile) {
		Image im = new Image(getClass().getResourceAsStream("commonMilkweed.png"));
		PlantImageView iv = new PlantImageView(im);
		iv.setPreserveRatio(true);
    	iv.setFitHeight(100);
    	setHandlerForDrag(iv);
    	setHandlerForPress(iv);
		tile.getChildren().add(iv);
		iv.setPaneLoc("tile");
	}
	
	@Override
	public void start(Stage stage) {
	    	setHandlerForDrag(view.iv1);
	    	setHandlerForPress(view.iv1);


	    	Scene scene = new Scene(view.bp, 800, 600);
	        stage.setScene(scene);
	        stage.show();
	    }
	public static void main(String[] args) {
		launch(args);
	}

}
