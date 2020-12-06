import javafx.scene.Node;
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

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class MainView {	
	FlowPane fp;
	Button newGardenButton; 
	Button loadGardenButton;
	BackgroundImage backgroundImage = new BackgroundImage(new Image("img/MainMenu.png"),BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	Scene scene;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	final static int NEWX = 500;
	final static int NEWY = 210;
	final static int LOADX = 350;
	final static int LOADY = 300;
	final static int BUTTON_HEIGHT = 50;
	final static int BUTTON_WIDTH = 150;
	
	/***
	 * Initializes the instance variables, sets the Scene, and displays it on Stage
	 * @param stage the Stage to display for MainView
	 */
	public MainView(Stage stage) {
		fp = new FlowPane();
		fp.setBackground(new Background(backgroundImage));
    	newGardenButton = new Button(" (+) New Garden");
    	newGardenButton.setTranslateX(NEWX);
    	newGardenButton.setTranslateY(NEWY);
    	loadGardenButton = new Button("Load Garden");
    	loadGardenButton.setTranslateX(LOADX);
    	loadGardenButton.setTranslateY(LOADY);
    	newGardenButton.setPrefHeight(BUTTON_HEIGHT);
    	newGardenButton.setPrefWidth(BUTTON_WIDTH);
    	loadGardenButton.setPrefHeight(BUTTON_HEIGHT);
    	loadGardenButton.setPrefWidth(BUTTON_WIDTH);
    	fp.getChildren().add(newGardenButton);
    	fp.getChildren().add(loadGardenButton);
    	scene = new Scene(fp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	stage.show();
	}
	
	/***
	 * Returns the most recent Scene for MainView
	 * @return the current Scene for MainView
	 */
	public Scene getScene() {
		return scene;
	}

	public Button getNewGardenButton() {
		return newGardenButton;
	}

	public Button getLoadGardenButton() {
		return loadGardenButton;
	}
}
