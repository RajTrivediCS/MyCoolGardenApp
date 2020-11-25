package Application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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
	File fileToLoad;
	public MainController(Stage stage) {
		this.stage = stage;
		fileChooser = new FileChooser();
		viewOne = new ViewOne(stage);
		desktop = Desktop.getDesktop();
	}
	public void handleLoadGardenButtonPress(MouseEvent e) {
		fileChooser.setTitle("Load Garden");
		fileToLoad = fileChooser.showOpenDialog(stage);
		deserializeGarden(fileToLoad);
	}

	public void handleNewGardenButtonPress(MouseEvent e) {
		stage.setScene(viewOne.getScene());
	}
	
	public Garden deserializeGarden(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Garden garden = (Garden)ois.readObject();
			System.out.println("Deserialized Plants: " + garden.gardensPlants.size());
			for(Plant p: garden.gardensPlants) {
				System.out.println(p.name);
			}
			ois.close();
			return garden;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
