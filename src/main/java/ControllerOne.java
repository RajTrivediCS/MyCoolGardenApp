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
	private File bg;
	/***
	 * Initializes the Stage to set the next Scene
	 * @param stage the Stage 
	 */
	public ControllerOne(Stage stage) {
		viewOne = new ViewOne(stage); 
	  	viewOne.getUploadImageButton().setOnMouseClicked(e -> handleUploadGardenButtonPress(e));
	  	viewOne.getSkipGardenImageButton().setOnMouseClicked(e->handleSkipGardenImageButton(e));
		this.stage = stage;
	}

	public void handleSkipGardenImageButton(MouseEvent e) {
		controllerTwo = new ControllerTwo(null, stage, viewOne.getHeightField().getText(), viewOne.getWidthField().getText());
		controllerTwo.getViewTwo().startShow();
	}

	/***
	 * Handles the event by setting the Scene for ViewTwo with user selected Background Image for Garden
	 * @param e the MouseEvent for click
	 */
	public void handleUploadGardenButtonPress(MouseEvent e) {
		FileChooser choose = new FileChooser();
		choose.setInitialDirectory(new File("bg/"));
		choose.setTitle("Select your background image");
		bg = choose.showOpenDialog(stage);
		//this is to stop program from crashing if you exit out of filechooser.
		if(getBg() == null) {
			return;
		}
		controllerTwo = new ControllerTwo(getBg(), stage, viewOne.getHeightField().getText(), viewOne.getWidthField().getText());
		controllerTwo.getViewTwo().startShow();
	}

	public ViewOne getViewOne() {
		return viewOne;
	}

	public File getBg() {
		return bg;
	}
}
