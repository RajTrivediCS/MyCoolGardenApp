import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller extends Application {

	View view;
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		view = new View(stage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
