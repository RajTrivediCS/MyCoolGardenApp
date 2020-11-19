package Application;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MainView {	
	FlowPane fp;
	Button newGardenButton; 
	Button loadGardenButton;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	public MainView(Stage stage) {
		fp = new FlowPane();
		fp.setStyle("-fx-background-color: #BFFF00");
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
    	fp.getChildren().add(newGardenButton);
    	fp.getChildren().add(loadGardenButton);
    	Scene scene = new Scene(fp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	stage.show();
	}
}
