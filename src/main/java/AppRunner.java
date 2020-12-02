
import javafx.application.Application;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class AppRunner extends Application {

	MainController mainController;
	
	@Override
	public void start(Stage stage) {
		mainController = new MainController(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
