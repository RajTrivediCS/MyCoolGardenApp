package Application;

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
import javafx.stage.Stage;

public class ControllerTwo {
	ViewTwo view;
	ModelTwo model;

	public ControllerTwo() {
		model = new ModelTwo();
		view = new ViewTwo();
	}
	public void serializeGarden(ModelTwo m) {
		try {
			FileOutputStream fos = new FileOutputStream("tempdata.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(m.garden);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Garden deserializeGarden() {
		try {
			FileInputStream fis = new FileInputStream("tempdata.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Garden garden = (Garden)ois.readObject();
			ois.close();
			return garden;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		//FIXME:USE SIZE SWITCH
		switch (iv.plant.plantSize) {
			case "small": iv.setFitHeight(90);
			break;
			case "medium": iv.setFitHeight(100);
			break;
			case "large": iv.setFitHeight(110);
			break;
		}
    	setHandlerForDrag(iv);
     	setHandlerForPress(view,iv);
    	int i = grid.getRowIndex(v);
		grid.add(iv, 0, i);
		iv.setPaneLoc("grid");
		view.sideView.add(iv);
	}
	
	//called when an item is added to the garden or deleted
	public ArrayList<Plant> updateGarden(){
		ArrayList<Plant>gard = new ArrayList<Plant>();
		for(PlantImageView p : view.plantsInGarden) {
			gard.add(p.plant);
		}
		return gard;
	}
	
	//handle dragging a plant image view to the flow pane
	public void drag(MouseEvent event, PlantImageView v) {
		Node n = (Node)event.getSource();
		n.setTranslateX(n.getTranslateX() + event.getX());
		n.setTranslateY(n.getTranslateY() + event.getY());
		v.setPaneLoc("flow");
		v.plant.setXLoc(v.getTranslateX());
		v.plant.setYLoc(v.getTranslateY());
	}	
	
	public void enter(ViewTwo view, MouseEvent event, PlantImageView v) {
		if(v.getPaneLoc().equals("grid")) {
			setHandlerForDrag(v);
			view.fp.getChildren().add(v);
			view.sideView.remove(v);
			handleReplaceImgView(view,view.gp, v);
			view.plantsInGarden.add(v);
		}
	}
	
	public void setHandlerForDrag(PlantImageView iv1) {
		iv1.setOnMouseDragged(event -> drag(event, iv1));
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
}
