package Application;


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

public class MainView {	
	FlowPane fp;
	Button newGardenButton; 
	Button loadGardenButton;
	MainController mainController;
	BackgroundImage backgroundImage = new BackgroundImage(new Image("img/MainMenu.png"),BackgroundRepeat.NO_REPEAT, 
			BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	Scene scene;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	public MainView(Stage stage) {
		mainController = new MainController(stage);
		
		fp = new FlowPane();
		fp.setBackground(new Background(backgroundImage));
    	newGardenButton = new Button(" (+) New Garden");
    	newGardenButton.setTranslateX(500);
    	newGardenButton.setTranslateY(210);
    	loadGardenButton = new Button("Load Garden");
    	loadGardenButton.setTranslateX(350);
    	loadGardenButton.setTranslateY(300);
    	newGardenButton.setPrefHeight(50);
    	newGardenButton.setPrefWidth(150);
    	loadGardenButton.setPrefHeight(50);
    	loadGardenButton.setPrefWidth(150);
    	newGardenButton.setOnMouseClicked(e-> mainController.handleNewGardenButtonPress(e));
    	loadGardenButton.setOnMouseClicked(e-> mainController.handleLoadGardenButtonPress(e));
    	fp.getChildren().add(newGardenButton);
    	fp.getChildren().add(loadGardenButton);
    	scene = new Scene(fp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	stage.show();
	}
	
	public Scene getScene() {
		return scene;
	}
}
