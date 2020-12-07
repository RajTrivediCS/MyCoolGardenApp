import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class MainController {
	MainView mainView;
	ControllerOne controllerOne;
	Stage stage;
	FileChooser fileChooser;
	File fileToLoad;
	ControllerTwo controllerTwo;
	
	/***
	 * Initializes the Stage to set the next Scene
	 * @param stage the Stage
	 */
	public MainController(Stage stage) {
		mainView = new MainView(stage);
		mainView.getNewGardenButton().setOnMouseClicked(e-> handleNewGardenButtonPress(e));
    	mainView.getLoadGardenButton().setOnMouseClicked(e-> handleLoadGardenButton(e));
		this.stage = stage;
		fileChooser = new FileChooser();
		controllerOne = new ControllerOne(stage);
	}
	
	/***
	 * Handles the event by setting Scene for ViewTwo after Deserializing Garden
	 * @param e the MouseEvent for click
	 */
	public void handleLoadGardenButton(MouseEvent e) {
		fileChooser.setTitle("Load Your Garden");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized File(*.ser)", "*.ser"));
		fileToLoad = fileChooser.showOpenDialog(stage);
		if(fileToLoad == null) {
			return;
		}
		Garden userSavedGarden = deserializeGarden(fileToLoad.getName());
		controllerTwo = new ControllerTwo(stage, userSavedGarden);
		controllerTwo.getViewTwo().startShow();
	}

	/***
	 * Handles the event by setting Scene for ViewOne
	 * @param e the MouseEvent for click
	 */
	public void handleNewGardenButtonPress(MouseEvent e) {
		stage.setScene(controllerOne.viewOne.getScene());
		controllerOne.getViewOne().startShow();
	}
	
	/***
	 * Renders the Garden from Deserialization
	 * @param file the name of the File that needs to be deserialized
	 * @return the Garden after deserializing
	 */
	public Garden deserializeGarden(String file) {
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
