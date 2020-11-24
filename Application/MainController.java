package Application;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {

	ViewOne viewOne;
	Stage stage;
	FileChooser fileChooser;
	File file;
	public MainController(Stage stage) {
		this.stage = stage;
		fileChooser = new FileChooser();
		viewOne = new ViewOne(stage);
	}
	public void handleLoadGardenButtonPress(MouseEvent e) {
		// stage.setScene(viewOne.getScene());
		file = fileChooser.showOpenDialog(stage);
		System.out.println(file.getAbsolutePath() + " selected...");
	}

	public void handleNewGardenButtonPress(MouseEvent e) {
		stage.setScene(viewOne.getScene());
	}
}
