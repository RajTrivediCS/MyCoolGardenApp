import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

public class Controller extends Application {
	Model model;
	View view;
	
	//handleAddingToGarden
	//handleMovingInGarden
	//handleDeletionFromGarden
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		view = new View(stage);	
		
		view.tilePane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = view.tilePane.startDragAndDrop(TransferMode.COPY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(view.imageTop);

                db.setContent(content);

                event.consume();
            }
        });
		
		view.flowPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if(db.hasImage()){
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            }
        });
		
		view.flowPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                ImageView imageView = new ImageView(db.getImage());
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(100);
                System.out.println("TranslateX: " + imageView.getTranslateX());
                System.out.println("TranslateY: " + imageView.getTranslateX());
                System.out.println("EventX: " + event.getX());
                System.out.println("EventY: " + event.getY());
                imageView.setTranslateX(event.getX() - imageView.getTranslateX());
                imageView.setTranslateY(event.getY() - imageView.getTranslateY());
                view.flowPane.getChildren().add(imageView);
                event.setDropCompleted(true);
            }
        });
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
