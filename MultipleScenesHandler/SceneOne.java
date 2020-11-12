package MultipleScenesHandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class SceneOne {	
	FlowPane fp;
	Button uploadGardenImageButton;
	Scene scene;
	public SceneOne(Stage stage){
		
		fp = new FlowPane();
    	fp.setStyle("-fx-background-color: #BFFF00");
    	uploadGardenImageButton = new Button("Upload Garden Image");
    	uploadGardenImageButton.setTranslateX(300);
    	uploadGardenImageButton.setTranslateY(300);
    	uploadGardenImageButton.setPrefHeight(75);
    	uploadGardenImageButton.setPrefWidth(200);
    	
    	// action event for Upload Garden Image Button
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	stage.setScene(new SceneContainer(stage).getSceneMap().get(SceneName.SCENE2));
            } 
        };
        
	    uploadGardenImageButton.setOnAction(event);
    	fp.getChildren().add(uploadGardenImageButton);
    	scene = new Scene(fp,800,600);
    	stage.setScene(scene);
    	stage.show();
	}
	public Scene getScene() {
		return scene;
	}
}
