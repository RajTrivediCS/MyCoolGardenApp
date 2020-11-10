import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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
	public List<ImageView> ivList = new ArrayList<ImageView>();
	Image imageTop = new Image(getClass().getResourceAsStream("img/commonMilkweed.png"));
	double IV1WIDTH = 100;
	double IV1HEIGHT = IV1WIDTH * imageTop.getHeight() / imageTop.getWidth();
	FlowPane flowPane = new FlowPane();
	TilePane tilePane = new TilePane();
	StackPane stackPane = new StackPane();
	private Controller imc;
	Button loadGardenButton;
	Button uploadImageButton;
	Button newGardenButton;
	
	public View(Stage theStage) {
		theStage.setTitle("Garden Design Application");
/*		
		//Load Garden Button
		loadGardenButton = new Button("Load Garden");
		loadGardenButton.setTranslateX(-60);
		loadGardenButton.setTranslateY(275);
		loadGardenButton.setTranslateZ(25);
		loadGardenButton.setPrefHeight(50);
		loadGardenButton.setPrefWidth(100);
		
		//Upload Image Button
		uploadImageButton = new Button("Upload Image");
		uploadImageButton.setTranslateX(40);
		uploadImageButton.setTranslateY(300);
		uploadImageButton.setTranslateZ(75);
		uploadImageButton.setPrefHeight(50);
		uploadImageButton.setPrefWidth(100);
		
		//New Garden Button
		newGardenButton = new Button("New Garden");
		newGardenButton.setTranslateX(-60);
		newGardenButton.setTranslateY(400);
		newGardenButton.setTranslateZ(55);
		newGardenButton.setPrefHeight(50);
		newGardenButton.setPrefWidth(100);
*/		
		tilePane.getChildren().add(stackPane);

		ivList.add(new ImageView());
		ivList.get(ivList.size() - 1).setImage(imageTop);
		ivList.get(ivList.size() - 1).setPreserveRatio(true);
		ivList.get(ivList.size() - 1).setFitHeight(IV1HEIGHT);
		ivList.get(ivList.size() - 1).setFitWidth(IV1WIDTH);
		stackPane.getChildren().add(ivList.get(ivList.size() - 1));
		
		flowPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
/*		
		// Adds all three Buttons to TilePane
		tilePane.getChildren().add(loadGardenButton);
		tilePane.getChildren().add(uploadImageButton);
		tilePane.getChildren().add(newGardenButton);
*/		
		
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
}

