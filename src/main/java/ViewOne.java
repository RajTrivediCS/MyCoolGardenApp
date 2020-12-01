import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ViewOne {	
	FlowPane fp;
	Button uploadGardenImageButton;
	Scene scene;
	ControllerOne controllerOne;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	
	/***
	 * Initializes the instance variables, sets the Scene, and displays it on Stage
	 * @param stage the Stage to display for ViewOne
	 */
	public ViewOne(Stage stage){
		controllerOne = new ControllerOne(stage);
		fp = new FlowPane();
    	BackgroundImage backgroundImage = new BackgroundImage(new Image("img/LoadImageBackground.jpg"),BackgroundRepeat.NO_REPEAT, 
    			BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
    	fp.setBackground(new Background(backgroundImage));
    	uploadGardenImageButton = new Button("Load Garden Background Image");
    	uploadGardenImageButton.setTranslateX(450);
    	uploadGardenImageButton.setTranslateY(300);
    	uploadGardenImageButton.setPrefHeight(75);
    	uploadGardenImageButton.setPrefWidth(200);
    	uploadGardenImageButton.setOnMouseClicked(e -> controllerOne.handleUploadGardenButtonPress(e));
    	fp.getChildren().add(uploadGardenImageButton);
    	scene = new Scene(fp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	stage.show();
	}
	
	/***
	 * Returns the most recent Scene for ViewOne
	 * @return the current Scene for ViewOne
	 */
	public Scene getScene() {
		return scene;
	}
}
