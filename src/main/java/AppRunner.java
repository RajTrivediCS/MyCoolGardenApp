
import javafx.application.Application;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class AppRunner extends Application {

	MainView mainView;
	
	@Override
	public void start(Stage stage) {
		mainView = new MainView(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
