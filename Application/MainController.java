package Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController {

	ViewOne viewOne;
	Stage stage;
	
	public MainController(Stage stage) {
		this.stage = stage;
		viewOne = new ViewOne(stage);
	}
	public void handleLoadGardenButtonPress(MouseEvent e) {
		stage.setScene(viewOne.getScene());
	}

	public void handleNewGardenButtonPress(MouseEvent e) {
		stage.setScene(viewOne.getScene());
	}
}
