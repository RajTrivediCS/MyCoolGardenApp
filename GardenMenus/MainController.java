package GardenMenus;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class MainController extends Application {

	MainScene mainScene;

	@Override
	public void start(Stage stage) throws Exception {
		mainScene = new MainScene(stage);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	stage.setScene(new SceneContainer(stage).getSceneMap().get(SceneName.SCENE1));
            } 
        };
        mainScene.newGardenButton.setOnAction(event);
        mainScene.loadGardenButton.setOnAction(event);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
