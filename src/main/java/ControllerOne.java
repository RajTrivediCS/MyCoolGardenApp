import java.io.File;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ControllerOne {
	ViewOne viewOne;
	Stage stage;
	ControllerTwo controllerTwo;
	/***
	 * Initializes the Stage to set the next Scene
	 * @param stage the Stage 
	 */
	public ControllerOne(Stage stage) {
		viewOne = new ViewOne(stage); 
	  	viewOne.uploadGardenImageButton.setOnMouseClicked(e -> handleUploadGardenButtonPress(e));
		this.stage = stage;
	}

	/***
	 * Handles the event by setting the Scene for ViewTwo with user selected Background Image for Garden
	 * @param e the MouseEvent for click
	 */
	public void handleUploadGardenButtonPress(MouseEvent e) {
		FileChooser choose = new FileChooser();
		choose.setTitle("Select your background image");
		File bg = choose.showOpenDialog(stage);
		controllerTwo = new ControllerTwo(bg, stage);
		controllerTwo.viewTwo.startShow();
	}
}
