import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class ControllerTwo {
	ViewTwo viewTwo;
	ModelTwo model = new ModelTwo();
	FileChooser loadFileChooser;
	FileChooser fileChooserSave;
	File fileToLoad;
	File fileToSave;
	Stage stage;
	int identifier;
	
	/***
	 * Initializes the instance variables
	 */
	public ControllerTwo(File bg, Stage stage) {
		loadFileChooser = new FileChooser();
		fileChooserSave = new FileChooser();
		viewTwo = new ViewTwo(stage, bg, model.hotBarPlants);
		for(PlantImageView p : viewTwo.sideView) {
	    	setHandlerForPress(p);
		}
    	model.garden.setBg(bg);
		this.stage = stage;
		identifier = 0;
		viewTwo.sortBy.setOnMouseClicked(e-> sortButtonHandler());
		setOnActionAdder();
	}
	
	public ControllerTwo(Stage stage, Garden userSavedGarden) {
		model.garden = userSavedGarden; 
		loadFileChooser = new FileChooser();
		fileChooserSave = new FileChooser(); 
		viewTwo = new ViewTwo(stage, model.garden.bg, model.hotBarPlants, model.garden.gardensPlants);
		for(PlantImageView p : viewTwo.sideView) {
	    	setHandlerForPress(p);
		}
		
		for(PlantImageView p : viewTwo.plantsInGarden) {
	    	setHandlerForDrag(p);
	    	setHandlerDeletePlant(p);
		}
		
		this.stage = stage;
		for(Plant p: model.garden.gardensPlants) {
    		if (p.id > identifier) {
    			identifier = p.id;
    		}
    	}
		identifier++;
		viewTwo.sortBy.setOnMouseClicked(e-> sortButtonHandler());
		setOnActionAdder();
	}

	/***
	 * Sets the current instance of ViewTwo with the given ViewTwo 
	 * @param v2 the ViewTwo to be set
	 */
	public void setViewTwo(ViewTwo v2) {
		this.viewTwo = v2;
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
	
	public void setOnActionAdder() {
    	viewTwo.newButton.setOnAction(e-> handleNewButtonPress(e));
    	viewTwo.loadButton.setOnAction(e-> handleLoadButtonPress(e));
    	viewTwo.saveButton.setOnAction(e-> handleSaveButton(stage));
    	viewTwo.gSoilButton.setOnAction(e-> handleGSoilButton(e));
    	viewTwo.gLightButton.setOnAction(e-> handleLightButton(e));
    	viewTwo.generateReport.setOnAction(e-> handleGenerateReport(e));
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
		viewTwo.sideView.add(iv);
	}
	
	/***
	 * Updates the Garden upon addition or deletion of something into the Garden
	 * @return the Garden after update
	 */
	public ArrayList<Plant> updateGarden(){
		ArrayList<Plant> gard = new ArrayList<Plant>();
		for(PlantImageView p : viewTwo.plantsInGarden) {
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
		for(PlantImageView p : viewTwo.plantsInGarden) {
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
			PlantImageView nv = v;
			setHandlerForDrag(nv);
			AnchorPane.setTopAnchor(nv, 0.0);
	    	AnchorPane.setLeftAnchor(nv, 0.0);
			viewTwo.ap.getChildren().add(nv);
			viewTwo.sideView.remove(v);
			handleReplaceImgView(viewTwo.gp, v);
			nv.plant.id = identifier;
			viewTwo.plantsInGarden.add(nv);
			setHandlerDeletePlant(nv);
			identifier++;
		}
	}
	
	public void setHandlerDeletePlant(PlantImageView iv1) {
		iv1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                MouseButton button = event.getButton();
                if(button==MouseButton.SECONDARY){
                	viewTwo.plantsInGarden.remove(iv1);
                	viewTwo.plantsInWasteBasket.add(iv1);
                	String plantWaste = "";
                	for (PlantImageView p:viewTwo.plantsInWasteBasket) {
            			plantWaste += p.plant.name + ", ";
            		}
                	Tooltip.install(viewTwo.wasteBasket, new Tooltip(plantWaste +"\n"));
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
		viewTwo.nameButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	viewTwo.sortSideView("name");
		    }
		});
		viewTwo.sunButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	viewTwo.sortSideView("sun");
		    }
		});
		viewTwo.soilButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	viewTwo.sortSideView("soil");
		    }
		});
		viewTwo.sizeButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        viewTwo.sortSideView("size");
		    }
		});
	}
	
	/***
	 * Handles the event by creating a brand new Scene for ViewTwo
	 * @param e the ActionEvent for pressing "New" Button(under File Menu)
	 */
	public void handleNewButtonPress(ActionEvent e) {
		File bGround = model.garden.getBg();
		viewTwo = new ViewTwo(stage, bGround, model.hotBarPlants);
		model = new ModelTwo();
		model.garden.setBg(bGround);
		setOnActionAdder();
		viewTwo.startShow();
	}

	/***
	 * Handles the event by deserializing Garden after user chooses file to open 
	 * @param e the ActionEvent for pressing "Load" Button(under File Menu)
	 */
	public void handleLoadButtonPress(ActionEvent e) {
		loadFileChooser.setTitle("Load Your Garden");
		loadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized File(*.ser)", "*.ser"));
		fileToLoad = loadFileChooser.showOpenDialog(viewTwo.stage);
		Garden userSavedGarden = deserializeGarden(fileToLoad);
		model.garden = userSavedGarden;
		viewTwo = new ViewTwo(stage, model.garden.bg, model.hotBarPlants, model.garden.gardensPlants);
		for(PlantImageView p : viewTwo.sideView) {
	    	setHandlerForPress(p);
		}
		for(PlantImageView p : viewTwo.plantsInGarden) {
	    	setHandlerForDrag(p);
	    	setHandlerDeletePlant(p);
		}
		
		for(Plant p: model.garden.gardensPlants) {
    		if (p.id > identifier) {
    			identifier = p.id;
    		}
    	}
		identifier++;
		setOnActionAdder();
		viewTwo.startShow();
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
	
	/***
	 * Handles the event by displaying the pop-up window for "soil" property of plant in separate Stage
	 * @param event the ActionEvent for "Choose Garden Soil" button
	 */
	public void handleGSoilButton(ActionEvent event){
		Stage popUp = viewTwo.makePopUpForSunSoil("soil");
        popUp.show();
        FlowPane pane = viewTwo.addButtonsToSoilPopUp();
        for(Node n : pane.getChildren())
        	switch((String) n.getUserData()) {
	        	case "All":
			        ((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenSoil="any";
					    }
					});;
					break;
	        	case "Loamy":
	        		((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenSoil="loamy";
					    }
					});;
					break;
	        	case "Sandy":	
					((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenSoil="sandy";
					    }
					});;
					break;
	        	case "Clay":
					((ButtonBase) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenSoil="clay";
					    }
					});;
        	}
        Scene popScene = new Scene(pane);
        popUp.setScene(popScene);
        popUp.show();
	}

	/***
	 * Handles the event by displaying the pop-up window for "light" property of plant in separate Stage
	 * @param event the ActionEvent for "Choose Garden Light" button
	 */
	public void handleLightButton(ActionEvent e){
		Stage popUp = viewTwo.makePopUpForSunSoil("light");
        FlowPane pane = viewTwo.addButtonsToLightPopUp();
        
        for(Node n : pane.getChildren())
        	switch((String) n.getUserData()) {
	        	case "All":
			        ((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenLight="any";
					    }
					});;
					break;
	        	case "Full":
	        		((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenLight="full";
					    }
					});;
					break;
	        	case "Partial":	
					((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenLight="partial";
					    }
					});;
					break;
	        	case "Shade":
					((ButtonBase) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenLight="shade";
					    }
					});;
        	}

        Scene popScene = new Scene(pane);
        popUp.setScene(popScene);
        popUp.show();
	}
	
	/***
	 * Generates the score for user's Garden by comparing "soil" and "light" properties of both Plant and Garden
	 * @return the generated score for user's Garden after comparing properties for both Plant and Garden
	 */
	public int generateScore() {
		double goodChoice = 1;
		double overallNumber = 1;
		for(Plant p : model.garden.gardensPlants) {
			overallNumber++;
				if(p.plantLight.contains(model.garden.gardenLight) | model.garden.gardenLight.equals("any")) {
					if(p.plantSoil.contains(model.garden.gardenSoil) | model.garden.gardenSoil.equals("any")) {
						goodChoice++;
					}else {
						goodChoice+=.75;
					}
				}
				else if(p.plantSoil.contains(model.garden.gardenSoil) | model.garden.gardenSoil.equals("any")){
					goodChoice+=.75;
				}
				else {
					goodChoice+=.05;
				}
		}
		double total = goodChoice / overallNumber;
		int score = (int) (total*100);
		return score;
	}
	
	/***
	 * Generates the report with multiple text statements and returns the report at the end
	 * @return the text of garden report for user's Garden
	 */
	public String generateReportText() {
		String report = "";
		int score = 0;
		ArrayList<Plant> arrayCopy = model.garden.gardensPlants;
		ArrayList<String> ignoreList = new ArrayList<String>();
		boolean ignore = false;
		if(arrayCopy.isEmpty()) {
			report += "There's nothing in the garden";
		}
		else {
			report += "There are ";
		}
		for(Plant p : arrayCopy) {
			int plantcount = 0;
			for(String s : ignoreList) {
				if(p.name.equals(s)) {
					ignore = true;
				}
			}
			if(!ignore) {
				for(Plant p2 : arrayCopy) {
					if(p2.name.equals(p.name)) {
						plantcount++;
					}
				}
				report += p.name + ": "+plantcount+"  ";
				ignoreList.add(p.name);
			}
			ignore = false;
		}
		report+= "in your garden.\n";
		if(model.garden.gardenLight == null | model.garden.gardenSoil == null) {
			report += "You need to go into the file-menu and pick your light and soil type.\n Then we can give you a proper score.";
		}
		else {
			report+= "Your garden's grade based on how your plants match your garden type is: ";
			score = generateScore();
			report+= score;
			report+= "%.\n";
			if(score>80) {
				report+="Great job creating your garden!";
			}
			else if(score<80 && score>60) {
				report += "Your garden just needs a little more work to get into tip top shape!";
			}
			else if(score<60 && score>40) {
				report += "Not bad, but your enviroment and garden aren't very compatible.";
			}
			else {
				report += "Maybe you selected the wrong garden soil or sun type. Check and then run this again";
			}
		}
		
		
		return report;
	}
	
	/***
	 * Handles the event by setting the Scene for Garden Report and displaying it as pop-up window
	 * @param e the ActionEvent for "Generate Garden Report" button
	 */
	public void handleGenerateReport(ActionEvent e) {
		Stage popUp = viewTwo.makePopUpForReport();
		String report = generateReportText();
		FlowPane pane = viewTwo.makeReportPane(report);
		Scene popScene = new Scene(pane);
        popUp.setScene(popScene);
        popUp.show();
	}
}
