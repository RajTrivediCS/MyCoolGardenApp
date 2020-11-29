package Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {

	ViewOne viewOne;
	Stage stage;
	FileChooser fileChooser;
	File fileToLoad;
	ViewTwo viewTwo;
	public MainController(Stage stage) {
		this.stage = stage;
		fileChooser = new FileChooser();
		viewOne = new ViewOne(stage);
	}
	public void handleLoadGardenButton(MouseEvent e) {
		fileChooser.setTitle("Load Garden");
		fileToLoad = fileChooser.showOpenDialog(stage);
		Garden userSavedGarden = deserializeGarden(fileToLoad);
		/*for(Plant p: userSavedGarden.gardensPlants) {
			System.out.println(p.name + " " + p.getXLoc() + " " + p.getYLoc());
		}*/
		
		//FIXME: Find a way to save the background image to the model. Maybe String of file path and then load file on the next line.
		viewTwo = new ViewTwo(stage,userSavedGarden, null);
	}

	public void handleNewGardenButtonPress(MouseEvent e) {
		stage.setScene(viewOne.getScene());
	}
	
	public Garden deserializeGarden(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Garden garden = (Garden)ois.readObject();
			ois.close();
			return garden;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
