package Application;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainView {	
	FlowPane fp;
	Button newGardenButton; 
	Button loadGardenButton;
	GridPane gp;
	Image undoImage = new Image("img/undoImage.png");
	Image redoImage = new Image("img/redoImage.png");
	ImageView undoImgView;
	ImageView redoImgView;
	
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	final static int MENU_BAR_WIDTH = 1380;
	final static int MENU_BAR_HEIGHT = 32;
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
