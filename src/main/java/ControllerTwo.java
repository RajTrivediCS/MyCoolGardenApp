import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ControllerTwo {
	ViewTwo view2;
	ModelTwo model;
	FileChooser loadFileChooser;
	FileChooser fileChooserSave;
	File fileToLoad;
	File fileToSave;
	Stage stage;
	int identifier;
	
	/***
	 * Initializes the instance variables
	 */
	public ControllerTwo() {
		loadFileChooser = new FileChooser();
		fileChooserSave = new FileChooser(); 
		identifier = 0;
	}
	
	/***
	 * Sets the current instance of ViewTwo with the given ViewTwo 
	 * @param v2 the ViewTwo to be set
	 */
	public void setViewTwo(ViewTwo v2) {
		this.view2 = v2;
	}
	
	/***
	 * Sets the current instance of ModelTwo with the given ModelTwo 
	 * @param m2 the ModelTwo to be set
	 */
	public void setModelTwo(ModelTwo m2) {
		this.model = m2;
	}
	
	/***
	 * Saves the most recent state of Garden to the given file input 
	 * @param file the file that is serialized
	 */
	public void serializeGarden(File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(model.garden);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/***
	 * Replaces image that was in the SideBar with an exact copy
	 * @param grid the GridPane to add Image
	 * @param v the PlantImageView to access the attributes of Plant 
	 */
	public void handleReplaceImgView(GridPane grid, PlantImageView v) {
		Image im = v.getImage();
		PlantImageView iv = new PlantImageView(new Plant(v.plant.name, v.plant.xLoc, v.plant.yLoc, 
				v.plant.plantLight, v.plant.plantSoil,v.plant.plantSize)); //change to use getters later
		iv.setImage(im);
		Tooltip tooltip =  new Tooltip("This is "+v.plant.name+".\n"+"It needs "+v.plant.plantLight+" and "+v.plant.plantSoil+".");
    	Tooltip.install(iv, tooltip);
		iv.setPreserveRatio(true);
		switch (iv.plant.plantSize) {
			case "small": iv.setFitHeight(90);
			break;
			case "medium": iv.setFitHeight(100);
			break;
			case "large": iv.setFitHeight(130);
			break;
		}
    	setHandlerForDrag(iv);
     	setHandlerForPress(iv);
    	int i = grid.getRowIndex(v);
		grid.add(iv, 0, i);
		iv.setPaneLoc("grid");
		view2.sideView.add(iv);
	}
	
	/***
	 * Updates the Garden upon addition or deletion of something into the Garden
	 * @return the Garden after update
	 */
	public ArrayList<Plant> updateGarden(){
		ArrayList<Plant> gard = new ArrayList<Plant>();
		for(PlantImageView p : view2.plantsInGarden) {
			gard.add(p.plant);
		}
		System.out.print(gard);
		return gard;
	}
	
	/***
	 * Handles dragging a plant image view to the flow pane
	 * @param event the MouseEvent for drag
	 * @param v the PlantImageView to update the location of that Plant
	 */
	//
	public void drag(MouseEvent event, PlantImageView v) {
		Node n = (Node)event.getSource();
		n.setTranslateX(n.getTranslateX() + event.getX());
		n.setTranslateY(n.getTranslateY() + event.getY());
		v.setPaneLoc("flow");
		v.plant.setXLoc(v.getTranslateX());
		v.plant.setYLoc(v.getTranslateY());
		for(PlantImageView p : view2.plantsInGarden) {
			if(p.plant.id == v.plant.id){
				p.plant.xLoc = v.plant.xLoc;
				p.plant.yLoc = v.plant.yLoc;
				System.out.println(p.plant.xLoc);
			}
		}
		model.garden.setGardensPlants(updateGarden());
	}	
	
	/***
	 * Handles starting the drag operation and replicating the PlantImageView in its location 
	 * @param event the MouseEvent for press
	 * @param v the PlantImageView to drag
	 */
	public void enter(MouseEvent event, PlantImageView v) {
		if(v.getPaneLoc().equals("grid")) {
			setHandlerForDrag(v);
			view2.fp.getChildren().add(v);
			view2.sideView.remove(v);
			handleReplaceImgView(view2.gp, v);
			v.plant.id = identifier;
			view2.plantsInGarden.add(v);
			setHandlerDeletePlant(v);
			identifier++;
		}
	}
	
	public void setHandlerDeletePlant(PlantImageView iv1) {
		iv1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                MouseButton button = event.getButton();
                if(button==MouseButton.SECONDARY){
                	view2.plantsInGarden.remove(iv1);
                	view2.plantsInWasteBasket.add(iv1);
                	String plantWaste = "";
                	for (PlantImageView p:view2.plantsInWasteBasket) {
            			plantWaste += p.plant.name + ", ";
            		}
                	Tooltip.install(view2.wasteBasket, new Tooltip(plantWaste +"\n"));
                	iv1.setImage(null);
                }
                model.garden.setGardensPlants(updateGarden());
            }
        });
	}
	
	/***
	 * Starts the event for drag
	 * @param iv1 the PlantImageView that is dragged
	 */
	public void setHandlerForDrag(PlantImageView iv1) {
		iv1.setOnMouseDragged(event -> drag(event, iv1));
	}
	
	
	/***
	 * Starts the event for press
	 * @param v the PlantImageView that is pressed
	 */
	public void setHandlerForPress(PlantImageView v) {
		v.setOnMousePressed(event->enter(event, v));
		
	}
	
	/***
	 * Sorts all the Plants depending on which button you click  
	 */
	public void sortButtonHandler() {
		view2.nameButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	view2.sortSideView("name");
		    }
		});
		view2.sunButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	view2.sortSideView("sun");
		    }
		});
		view2.soilButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	view2.sortSideView("soil");
		    }
		});
	}
	
	/***
	 * Handles the event by creating a brand new Scene for ViewTwo
	 * @param e the ActionEvent for pressing "New" Button(under File Menu)
	 */
	public void handleNewButtonPress(ActionEvent e) {
		System.out.println("newButton press");
		view2 = new ViewTwo(view2.stage, model.garden.getBg());
	}

	/***
	 * Handles the event by deserializing Garden after user chooses file to open 
	 * @param e the ActionEvent for pressing "Load" Button(under File Menu)
	 */
	public void handleLoadButtonPress(ActionEvent e) {
		loadFileChooser.setTitle("Load Your Garden");
		loadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized File(*.ser)", "*.ser"));
		fileToLoad = loadFileChooser.showOpenDialog(view2.stage);
		Garden userSavedGarden = deserializeGarden(fileToLoad);
		view2 = new ViewTwo(view2.stage, userSavedGarden);
	}
	
	/***
	 * Renders the Garden from Deserialization
	 * @param file the File that needs to be deserialized
	 * @return the Garden after deserializing
	 */
	public Garden deserializeGarden(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Garden garden = (Garden)ois.readObject();
			ois.close();
			return garden;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * Handles the event by creating the file to save and serializing Garden to that file
	 * @param stage the Stage
	 */
	public void handleSaveButton(Stage stage) {
		fileChooserSave.setTitle("Save Your Garden");
		fileChooserSave.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized File(*.ser)", "*.ser"));
		fileToSave = fileChooserSave.showSaveDialog(stage);
		if(!fileToSave.getName().contains(".")) {
			fileToSave = new File(fileToSave.getAbsolutePath() + ".ser");
	    }
		serializeGarden(fileToSave);
	}
}
