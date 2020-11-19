import MultipleScenesHandler.*;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

 
public class Controller extends Application  {
	Model model = new Model();	
	View view= new View(model.getHotBarPlants());
	Map<SceneName,Scene> sceneMap;
	
	public void serializeGarden(Model m) {
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
	public void handleReplaceImgView(GridPane grid, PlantImageView v) {
		Image im = v.getImage();
		PlantImageView iv = new PlantImageView(v.plant);
		iv.setImage(im);
		Tooltip tooltip =  new Tooltip("This is "+v.plant.name+".\n"+"It needs "+v.plant.plantLight+" and "+v.plant.plantSoil+".");
    	Tooltip.install(iv, tooltip);
		iv.setPreserveRatio(true);
    	iv.setFitHeight(100);
    	setHandlerForDrag(iv);
    	setHandlerForPress(iv);
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
	
	//handle clicking on a plantImageView
	public void enter(MouseEvent event, PlantImageView v) {
		if(v.getPaneLoc().equals("grid")) {
			view.fp.getChildren().add(v);
			view.sideView.remove(v);
			handleReplaceImgView(view.gp, v);
			view.plantsInGarden.add(v);
		}
	}
	
	public void setHandlerForDrag(PlantImageView iv1) {
		iv1.setOnMouseDragged(event -> drag(event, iv1));
		model.garden.setGardensPlants(updateGarden());
	}
	
	public void setHandlerForPress(PlantImageView v) {
		v.setOnMousePressed(event->enter(event, v));
	}
	
	//sorts the sideView on click
	public void sortButtonHandler() {
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

	
	@Override
	public void start(Stage stage) {
		sceneMap = new SceneContainer(stage).getSceneMap();
		sortButtonHandler();
	    for(PlantImageView v : view.sideView) {
			setHandlerForDrag(v);
	    	setHandlerForPress(v);
	    }
	    
	    // action event for New Garden Button and Load Garden Button
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	stage.setScene(sceneMap.get(SceneName.SCENE1));
            } 
        };
        
	    //view.loadGardenButton.setOnAction(event); FIXME: this needs to go in a different controller
	    //view.newGardenButton.setOnAction(event);
	    
	    Scene scene = new Scene(view.getBP(), 800, 600);
	    stage.setScene(scene);
	    stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}