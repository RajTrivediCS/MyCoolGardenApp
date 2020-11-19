package Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class MainController extends Application {

	MainView mainView;
	@Override
	public void start(Stage stage) throws Exception {
		mainView = new MainView(stage);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	stage.setScene(new ViewsContainer(stage).getSceneMap().get(ViewName.SCENE1));
            } 
        };
        mainView.newGardenButton.setOnAction(event);
        mainView.loadGardenButton.setOnAction(event);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
