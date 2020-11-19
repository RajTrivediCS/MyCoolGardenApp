package Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ViewOne {	
	FlowPane fp;
	Button uploadGardenImageButton;
	Scene scene;
	Image undoImage = new Image("img/undoImage.png");
	Image redoImage = new Image("img/redoImage.png");
	ImageView undoImgView;
	ImageView redoImgView;
	final static int WIDTH = 800;
	final static int HEIGHT = 600;
	final static int MENU_BAR_WIDTH = 1380;
	final static int MENU_BAR_HEIGHT = 32;
	public ViewOne(Stage stage){
		fp = new FlowPane();
    	fp.setStyle("-fx-background-color: #BFFF00");
    	uploadGardenImageButton = new Button("Upload Garden Image");
    	uploadGardenImageButton.setTranslateX(450);
    	uploadGardenImageButton.setTranslateY(300);
    	uploadGardenImageButton.setPrefHeight(75);
    	uploadGardenImageButton.setPrefWidth(200);
    	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	stage.setScene(new ViewsContainer(stage).getSceneMap().get(ViewName.SCENE2));
            } 
        };
        uploadGardenImageButton.setOnAction(event);
    	fp.getChildren().add(uploadGardenImageButton);
    	scene = new Scene(fp,WIDTH,HEIGHT);
    	stage.setScene(scene);
    	stage.show();
	}
	public Scene getScene() {
		return scene;
	}
}
