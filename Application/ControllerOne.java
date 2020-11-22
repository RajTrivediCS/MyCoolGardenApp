package Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerOne {

	Stage stage;
	ViewTwo viewTwo;
	public ControllerOne(Stage stage) {
		viewTwo = new ViewTwo(stage);
		this.stage = stage;
	}

	public void handleUploadGardenButtonPress(MouseEvent e) {
		// TODO Auto-generated method stub
		stage.setScene(viewTwo.getScene());
		
	}
}
