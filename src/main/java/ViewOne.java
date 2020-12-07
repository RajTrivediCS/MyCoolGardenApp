import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ViewOne {	
	AnchorPane ap;
	Button uploadGardenImageButton;
	Button skipGardenImageButton;
	Scene scene;
	Stage stage;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	final static int BUTTONX = 450;
	final static int LOAD_BUTTONY = 400;
	final static int SKIP_BUTTONY = 200;
	final static int BUTTON_HEIGHT = 75;
	final static int BUTTON_WIDTH = 200;
	
	/***
	 * Initializes the instance variables, sets the Scene, and displays it on Stage
	 * @param stage the Stage to display for ViewOne
	 */
	public ViewOne(Stage stage){
		this.stage = stage;
		ap = new AnchorPane();
    	BackgroundImage backgroundImage = new BackgroundImage(new Image("img/LoadImageBackground.jpg"),BackgroundRepeat.NO_REPEAT, 
    			BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
    	ap.setBackground(new Background(backgroundImage));
    	uploadGardenImageButton = new Button("Load Garden Background Image");
    	skipGardenImageButton = new Button("No Background");
    	
    	uploadGardenImageButton.setTranslateX(BUTTONX);
    	uploadGardenImageButton.setTranslateY(LOAD_BUTTONY);
    	uploadGardenImageButton.setPrefHeight(BUTTON_HEIGHT);
    	uploadGardenImageButton.setPrefWidth(BUTTON_WIDTH);
    	
    	skipGardenImageButton.setTranslateX(BUTTONX);
    	skipGardenImageButton.setTranslateY(SKIP_BUTTONY);
    	skipGardenImageButton.setPrefHeight(BUTTON_HEIGHT);
    	skipGardenImageButton.setPrefWidth(BUTTON_WIDTH);
    	ap.getChildren().add(uploadGardenImageButton);
    	ap.getChildren().add(skipGardenImageButton);
    	scene = new Scene(ap,WIDTH,HEIGHT);
	}
	public void startShow() {
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
	public Button getUploadImageButton() {
		// TODO Auto-generated method stub
		return uploadGardenImageButton;
	}
}
