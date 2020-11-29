package Application;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerOne {

	Stage stage;
	ViewTwo viewTwo;
	public ControllerOne(Stage stage) {
		this.stage = stage;
	}

	public void handleUploadGardenButtonPress(MouseEvent e) {
		FileChooser choose = new FileChooser();
		choose.setTitle("Select your background image");
		File bg = choose.showOpenDialog(stage);
		viewTwo = new ViewTwo(stage, bg);
		stage.setScene(viewTwo.getScene());
	}
}
