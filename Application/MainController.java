package Application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
	Desktop desktop;
	File file;
	public MainController(Stage stage) {
		this.stage = stage;
		fileChooser = new FileChooser();
		viewOne = new ViewOne(stage);
		desktop = Desktop.getDesktop();
	}
	public void handleLoadGardenButtonPress(MouseEvent e) {
		// Earlier version...
		// stage.setScene(viewOne.getScene());
		
		// New Version
		fileChooser.setTitle("Load Garden");
		file = fileChooser.showOpenDialog(stage);
		try {
			desktop.open(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void handleNewGardenButtonPress(MouseEvent e) {
		stage.setScene(viewOne.getScene());
	}
}
