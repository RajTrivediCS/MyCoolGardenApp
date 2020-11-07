import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View {
	double xMinLeftBar, yMinLeftBar;
	double xMaxLeftBar, yMaxLeftBar;
	double xMinWorkSpace, yMinWorkSpace;
	double xMaxWorkSpace, yMaxWorkSpace;
	double xMinWasteBasket;
	double yMinWasteBasket;
	double xMaxWasteBasket;
	double yMaxWasteBasket;
	int canvasWidth = 700;
	int canvasHeight = 700;
	private final int WIDTH = canvasWidth;
	private final int HEIGHT = canvasHeight;
	private List<ImageView> ivList = new ArrayList<ImageView>();
	Image imageTop = new Image(getClass().getResourceAsStream("img/commonMilkweed.png"));
	double IV1WIDTH = 100;
	double IV1HEIGHT = IV1WIDTH * imageTop.getHeight() / imageTop.getWidth();
	FlowPane flowPane = new FlowPane();
	TilePane tilePane = new TilePane();
	StackPane stackPane = new StackPane();
	private Controller imc;
	public View(Stage theStage) {
		System.out.println(imageTop.getWidth());
		System.out.println(imageTop.getHeight());
		System.out.println(imageTop.getRequestedWidth());
		System.out.println(imageTop.getRequestedHeight());

		tilePane.getChildren().add(stackPane);
/*
		this.addImage(false);
		this.addImage(true);
*/
		ivList.add(new ImageView());
		ivList.get(ivList.size() - 1).setImage(imageTop);
		ivList.get(ivList.size() - 1).setPreserveRatio(true);
		ivList.get(ivList.size() - 1).setFitHeight(IV1HEIGHT);
		ivList.get(ivList.size() - 1).setFitWidth(IV1WIDTH);
		stackPane.getChildren().add(ivList.get(ivList.size() - 1));
		
		flowPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		tilePane.setPrefWidth(WIDTH * 0.3);
		tilePane.setPrefWidth(HEIGHT * 0.3);
		tilePane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		BorderPane borderPane = new BorderPane();
		borderPane.setRight(flowPane);
		borderPane.setLeft(tilePane);

		Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
		theStage.setScene(scene);
		theStage.show();
	}
/*	
	public void addImage(boolean draggable) {
		ivList.add(new ImageView());
		ivList.get(ivList.size() - 1).setImage(imageTop);
		ivList.get(ivList.size() - 1).setPreserveRatio(true);
		ivList.get(ivList.size() - 1).setFitHeight(IV1HEIGHT);
		ivList.get(ivList.size() - 1).setFitWidth(IV1WIDTH);
		System.out.println(ivList.get(ivList.size() - 1).getFitWidth() + ", " + IV1WIDTH);
		System.out.println(ivList.get(ivList.size() - 1).getFitHeight() + ", " + IV1HEIGHT);

		if (draggable) {
			ivList.get(ivList.size() - 1).setOnMouseDragged(imc.getHandlerForDrag());
			ivList.get(ivList.size() - 1).setOnMouseDragReleased(imc.getHandlerForDrop());
			ivList.get(ivList.size() - 1).setOnMouseReleased(imc.getHandlerForDrop());
		}
		ivList.get(ivList.size() - 1).setTranslateX(ivList.get(ivList.size() - 1).getTranslateX());
		ivList.get(ivList.size() - 1).setTranslateY(ivList.get(ivList.size() - 1).getTranslateY());
		ivList.get(ivList.size() - 1).setLayoutX(ivList.get(ivList.size() - 1).getLayoutX());
		ivList.get(ivList.size() - 1).setLayoutY(ivList.get(ivList.size() - 1).getLayoutY());

		System.out.println("ivList.size() = " + ivList.size());
		stackPane.getChildren().add(ivList.get(ivList.size() - 1));

	}
*/
}
