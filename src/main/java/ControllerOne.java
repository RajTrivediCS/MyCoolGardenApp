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
	private ViewOne viewOne;
	private Stage stage;
	private ControllerTwo controllerTwo;
	/***
	 * Initializes the Stage to set the next Scene
	 * @param stage the Stage 
	 */
	public ControllerOne(Stage stage) {
		viewOne = new ViewOne(stage); 
	  	viewOne.getUploadImageButton().setOnMouseClicked(e -> handleUploadGardenButtonPress(e));
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
		//this is to stop program from crashing if you exit out of filechooser.
		if(bg == null) {
			return;
		}
		controllerTwo = new ControllerTwo(bg, stage, viewOne.getHeightField().getText(), viewOne.getWidthField().getText());
		controllerTwo.getViewTwo().startShow();
	}

	public ViewOne getViewOne() {
		return viewOne;
	}
}
