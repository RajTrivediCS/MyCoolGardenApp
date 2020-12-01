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

	Stage stage;
	ViewTwo viewTwo;
	/***
	 * Initializes the Stage to set the next Scene
	 * @param stage the Stage 
	 */
	public ControllerOne(Stage stage) {
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
		viewTwo = new ViewTwo(stage, bg);
		stage.setScene(viewTwo.getScene());
	}
}
