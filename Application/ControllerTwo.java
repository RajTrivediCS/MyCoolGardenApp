package Application;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerTwo {
	ViewTwo view;
	ModelTwo model;
	FileChooser fileChooserSave;
	File fileToSave;
	int identifier;
	
	public ControllerTwo() {
		model = new ModelTwo();
		view = new ViewTwo();
		fileChooserSave = new FileChooser();
		identifier = 0;
	}
	
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
	

	//replaces image that was in the sidebar with an exact copy
	public void handleReplaceImgView(ViewTwo view, GridPane grid, PlantImageView v) {
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
			case "large": iv.setFitHeight(110);
			break;
		}
    	setHandlerForDrag(iv, view);
     	setHandlerForPress(view,iv);
    	int i = grid.getRowIndex(v);
		grid.add(iv, 0, i);
		iv.setPaneLoc("grid");
		view.sideView.add(iv);
	}
	
	//called when an item is added to the garden or deleted
	public ArrayList<Plant> updateGarden(){
		ArrayList<Plant> gard = new ArrayList<Plant>();
		for(PlantImageView p : view.plantsInGarden) {
			gard.add(p.plant);
		}
		return gard;
	}
	
	//handle dragging a plant image view to the flow pane
	public void drag(ViewTwo view, MouseEvent event, PlantImageView v) {
		Node n = (Node)event.getSource();
		n.setTranslateX(n.getTranslateX() + event.getX());
		n.setTranslateY(n.getTranslateY() + event.getY());
		v.setPaneLoc("flow");
		v.plant.setXLoc(v.getTranslateX());
		v.plant.setYLoc(v.getTranslateY());
		for(PlantImageView p : view.plantsInGarden) {
			if(p.plant.id == v.plant.id){
				p.plant.xLoc = v.plant.xLoc;
				p.plant.yLoc = v.plant.yLoc;
				System.out.println(p.plant.xLoc);
			}
		}
	}	
	
	public void enter(ViewTwo view, MouseEvent event, PlantImageView v) {
		if(v.getPaneLoc().equals("grid")) {
			setHandlerForDrag(v, view);
			view.fp.getChildren().add(v);
			view.sideView.remove(v);
			handleReplaceImgView(view,view.gp, v);
			v.plant.id = identifier;
			view.plantsInGarden.add(v);
			identifier++;
		}
	}
	
	public void setHandlerForDrag(PlantImageView iv1, ViewTwo view) {
		iv1.setOnMouseDragged(event -> drag(view, event, iv1));
		model.garden.setGardensPlants(updateGarden());
	}
	
	
	public void setHandlerForPress(ViewTwo view, PlantImageView v) {
		v.setOnMousePressed(event->enter(view, event, v));
		
	}
	
	public void sortButtonHandler(ViewTwo view) {
		view.nameButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        view.sortSideView("name");
		    }
		});
		view.sunButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        view.sortSideView("sun");
		    }
		});
		view.soilButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        view.sortSideView("soil");
		    }
		});
	}

	public void handleSaveButton(Stage stage) {
		fileChooserSave.setTitle("Save Garden");
		fileChooserSave.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized File(*.ser)", "*.ser"));
		fileToSave = fileChooserSave.showSaveDialog(stage);
		if(!fileToSave.getName().contains(".")) {
			fileToSave = new File(fileToSave.getAbsolutePath() + ".ser");
	    }
		serializeGarden(fileToSave);
	}
}
