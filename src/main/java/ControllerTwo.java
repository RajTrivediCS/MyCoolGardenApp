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
	final int REPLACE_SIZE = 130;
	
	/***
	 * Initializes the instance variables
	 */
	public ControllerTwo(File bg, Stage stage) {
		loadFileChooser = new FileChooser();
		fileChooserSave = new FileChooser();
		viewTwo = new ViewTwo(stage, bg, model.getHotBarPlants());
		for(PlantImageView p : viewTwo.getSideView()) {
	    	setHandlerForPress(p);
		}
    	model.getGarden().setBg(bg);
		this.stage = stage;
		identifier = 0;
		viewTwo.getSortBy().setOnMouseClicked(e-> sortButtonHandler());
		setOnActionAdder();
	}
	
	public ControllerTwo(Stage stage, Garden userSavedGarden) {
		model.garden = userSavedGarden; 
		loadFileChooser = new FileChooser();
		fileChooserSave = new FileChooser(); 
		viewTwo = new ViewTwo(stage, model.getGarden().getBg(), model.getHotBarPlants(), model.getGarden().getGardensPlants());
		for(PlantImageView p : viewTwo.getSideView()) {
	    	setHandlerForPress(p);
		}
		
		for(PlantImageView p : viewTwo.getPlantsInGarden()) {
	    	setHandlerForDrag(p);
	    	setHandlerDeletePlant(p);
		}
		
		this.stage = stage;
		for(Plant p: model.getGarden().getGardensPlants()) {
    		if (p.getID() > identifier) {
    			identifier = p.getID();
    		}
    	}
		identifier++;
		viewTwo.getSortBy().setOnMouseClicked(e-> sortButtonHandler());
		setOnActionAdder();
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
    	viewTwo.getNewButton().setOnAction(e-> handleNewButtonPress(e));
    	viewTwo.getLoadButton().setOnAction(e-> handleLoadButtonPress(e));
    	viewTwo.getSaveButton().setOnAction(e-> handleSaveButton(stage));
    	viewTwo.getGSoilButton().setOnAction(e-> handleGSoilButton(e));
    	viewTwo.getGLightButton().setOnAction(e-> handleLightButton(e));
    	viewTwo.getGenerateReport().setOnAction(e-> handleGenerateReport(e));
	}
	

	/***
	 * Replaces image that was in the SideBar with an exact copy
	 * @param grid the GridPane to add Image
	 * @param v the PlantImageView to access the attributes of Plant 
	 */
	public void handleReplaceImgView(GridPane grid, PlantImageView v) {
		Image im = v.getImage();
		PlantImageView iv = new PlantImageView(new Plant(v.getPlant().getName(), v.getPlant().getXLoc(), v.getPlant().getYLoc(), 
				v.getPlant().getPlantLight(), v.getPlant().getPlantSoil(),v.getPlant().getPlantSize()));
		iv.setImage(im);
		Tooltip tooltip =  new Tooltip("This is "+v.getPlant().getName() + ".\n"+"It needs "+v.getPlant().getPlantLight() + " and " +
				v.getPlant().getPlantSoil()+". \nIt is roughly " + v.getPlant().getPlantSize() + " feet in diameter.");
    	Tooltip.install(iv, tooltip);
		iv.setPreserveRatio(true);
		iv.setFitHeight(REPLACE_SIZE);  
    	setHandlerForDrag(iv);
     	setHandlerForPress(iv);
    	int i = grid.getRowIndex(v);
		grid.add(iv, 0, i);
		iv.setPaneLoc("grid");
		viewTwo.getSideView().add(iv);
	}
	
	/***
	 * Updates the Garden upon addition or deletion of something into the Garden
	 * @return the Garden after update
	 */
	public ArrayList<Plant> updateGarden(){
		ArrayList<Plant> gard = new ArrayList<Plant>();
		for(PlantImageView p : viewTwo.getPlantsInGarden()) {
			gard.add(p.getPlant());
		}
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
		v.getPlant().setXLoc(v.getTranslateX());
		v.getPlant().setYLoc(v.getTranslateY());
		for(PlantImageView p : viewTwo.getPlantsInGarden()) {
			if(p.getPlant().getID() == v.getPlant().getID()){
				p.getPlant().setXLoc(v.getPlant().getXLoc());
				p.getPlant().setYLoc(v.getPlant().getYLoc());
			}
		}
		model.getGarden().setGardensPlants(updateGarden());
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
			viewTwo.getAP().getChildren().add(nv);
			int size = viewTwo.setHeightFormula(Double.parseDouble(nv.getPlant().getPlantSize()));
			nv.setFitHeight(size);
			nv.setTranslateX(event.getX());
			nv.setTranslateY(event.getY());
			viewTwo.getSideView().remove(v);
			handleReplaceImgView(viewTwo.getGP(), v);
			nv.getPlant().setID(identifier);
			viewTwo.getPlantsInGarden().add(nv);
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
                	viewTwo.getPlantsInGarden().remove(iv1);
                	viewTwo.getPlantsInWasteBasket().add(iv1);
                	String plantWaste = "";
                	for (PlantImageView p:viewTwo.getPlantsInWasteBasket()) {
            			plantWaste += p.getPlant().getName() + ", ";
            		}
                	Tooltip.install(viewTwo.getWasteBasketIV(), new Tooltip(plantWaste +"\n"));
                	iv1.setImage(null);
                }
                model.getGarden().setGardensPlants(updateGarden());
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
		File bGround = model.getGarden().getBg();
		viewTwo = new ViewTwo(stage, bGround, model.getHotBarPlants());
		model = new ModelTwo();
		model.getGarden().setBg(bGround);
		setOnActionAdder();
		for(PlantImageView p : viewTwo.getSideView()) {
	    	setHandlerForPress(p);
		}
		viewTwo.startShow();
	}

	/***
	 * Handles the event by deserializing Garden after user chooses file to open 
	 * @param e the ActionEvent for pressing "Load" Button(under File Menu)
	 */
	public void handleLoadButtonPress(ActionEvent e) {
		loadFileChooser.setTitle("Load Your Garden");
		loadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized File(*.ser)", "*.ser"));
		fileToLoad = loadFileChooser.showOpenDialog(viewTwo.getStage());
		//this is to stop program from crashing if you exit out of filechooser.
		if(fileToLoad == null) {
			return;
		}
		Garden userSavedGarden = deserializeGarden(fileToLoad);
		model.setGarden(userSavedGarden);
		viewTwo = new ViewTwo(stage, model.getGarden().getBg(), model.getHotBarPlants(), model.getGarden().getGardensPlants());
		for(PlantImageView p : viewTwo.getSideView()) {
	    	setHandlerForPress(p);
		}
		for(PlantImageView p : viewTwo.getPlantsInGarden()) {
	    	setHandlerForDrag(p);
	    	setHandlerDeletePlant(p);
		}
		
		for(Plant p: model.getGarden().getGardensPlants()) {
    		if (p.getID() > identifier) {
    			identifier = p.getID();
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
		if(fileToSave == null) {
			return;
		}
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
		viewTwo.setPopUp(viewTwo.makePopUpForSunSoil("soil"));
        FlowPane pane = viewTwo.addButtonsToSoilPopUp();
        for(Node n : pane.getChildren())
        	switch((String) n.getUserData()) {
	        	case "All":
			        ((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensSoil("any");
					      viewTwo.getPopUp().hide();
					    }
					});;
					break;
	        	case "Loamy":
	        		((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensSoil("loamy");
					      viewTwo.getPopUp().hide();
					    }
					});;
					break;
	        	case "Sandy":	
					((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensSoil("sandy");
					      viewTwo.getPopUp().hide();
					    }
					});;
					break;
	        	case "Clay":
					((ButtonBase) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensSoil("clay");
					      viewTwo.getPopUp().hide();
					    }
					});;
        	}
        Scene popScene = new Scene(pane);
        viewTwo.getPopUp().setScene(popScene);
        viewTwo.getPopUp().show();
	}

	/***
	 * Handles the event by displaying the pop-up window for "light" property of plant in separate Stage
	 * @param event the ActionEvent for "Choose Garden Light" button
	 */
	public void handleLightButton(ActionEvent e){
		viewTwo.setPopUp(viewTwo.makePopUpForSunSoil("light"));
        FlowPane pane = viewTwo.addButtonsToLightPopUp();
        for(Node n : pane.getChildren())
        	switch((String) n.getUserData()) {
	        	case "All":
			        ((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensLight("any");
					      viewTwo.getPopUp().hide();
					    }
					});;
					break;
	        	case "Full":
	        		((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensLight("full");
					      viewTwo.getPopUp().hide();
					    }
					});;
					break;
	        	case "Partial":	
					((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensLight("partial");
					      viewTwo.getPopUp().hide();
					    }
					});;
					break;
	        	case "Shade":
					((ButtonBase) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.getGarden().setGardensLight("shade");
					      viewTwo.getPopUp().hide();
					    }
					});;
        	}

        Scene popScene = new Scene(pane);
        viewTwo.popUp.setScene(popScene);
        viewTwo.popUp.show();
	}
	
	/***
	 * Generates the score for user's Garden by comparing "soil" and "light" properties of both Plant and Garden
	 * @return the generated score for user's Garden after comparing properties for both Plant and Garden
	 */
	public int generateScore() {
		double goodChoice = 1;
		double overallNumber = 1;
		for(Plant p : model.getGarden().getGardensPlants()) {
			overallNumber++;
				if(p.getPlantLight().contains(model.getGarden().getGardensLight()) | model.getGarden().getGardensLight().equals("any")| p.getPlantLight().equals("any sun")) {
					if(p.getPlantSoil().contains(model.getGarden().getGardensSoil()) | model.getGarden().getGardensSoil().equals("any") | p.getPlantSoil().equals("any soil")) {
						goodChoice++;
					}else {
						goodChoice+=.75;
					}
				}
				
				else if(p.getPlantSoil().contains(model.getGarden().getGardensSoil()) | model.getGarden().getGardensSoil().equals("any")| p.getPlantSoil().equals("any soil")){
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
		ArrayList<Plant> arrayCopy = model.getGarden().getGardensPlants();
		ArrayList<String> ignoreList = new ArrayList<String>();
		boolean ignore = false;
		int newLineChecker = 1;
		if(arrayCopy.isEmpty()) {
			report += "There's nothing in the garden.\n";
		}
		else {
			report += "There are ";
			for(Plant p : arrayCopy) {
				int plantcount = 0;
				for(String s : ignoreList) {
					if(p.name.equals(s)) {
						ignore = true;
					}
				}
				if(!ignore) {
					for(Plant p2 : arrayCopy) {
						if(p2.getName().equals(p.getName())) {
							plantcount++;
						}
					}
					report += plantcount + " "+p.getName()+" and ";
					if(newLineChecker%4 == 0) {
						report+="\n";
					}
					ignoreList.add(p.getName());
				}
				newLineChecker++;
				ignore = false;
			}
			report+= "in your garden.\n";
		}
		if(model.getGarden().getGardensLight() == null | model.getGarden().getGardensSoil() == null) {
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
				report += "Good job, but a few plants are unhappy.";
			}
			else if(score<60 && score>40) {
				report += "Not bad, but some plants are unhappy.";
			}
			else {
				report += "Your garden's conditions is not suited for your plants.";
			}
		}
		return report;
	}
	
	public String getUnhappyPlants() {
		int numUnhappy = 0;
		String ignoreList = "";
		String unhappyPlants = "Here are some things you could work on:\n";
		if(model.getGarden().getGardensLight() == null | model.getGarden().getGardensSoil() == null) {
			return "";
		}
		ignoreList += "";
		for(Plant p : model.getGarden().getGardensPlants()) {
			if(!ignoreList.contains(p.getName())) {
				if(p.getPlantLight().contains(model.getGarden().getGardensLight()) | model.getGarden().getGardensLight().equals("any")| p.getPlantLight().equals("any sun")) {
					if(p.getPlantSoil().contains(model.getGarden().getGardensSoil()) | model.getGarden().getGardensSoil().equals("any") | p.getPlantSoil().equals("any soil")) {
						//plant is happy; do nothing.
					}
					
					else {
						unhappyPlants += "The " + p.getName() + " needs " + p.getPlantSoil() + " to be happy.\n";
					}
				}
				
				else if(p.getPlantSoil().contains(model.getGarden().getGardensSoil()) | model.getGarden().getGardensSoil().equals("any")| p.getPlantSoil().equals("any soil")){
					unhappyPlants += "The " + p.getName() + " needs " + p.getPlantLight() + " to be happy.\n";
				}
				
				else {
					unhappyPlants += "The " + p.getName() + " needs " + p.getPlantLight() + " and " + p.getPlantSoil() + " to be happy.\n";
				}
				numUnhappy++;
				ignoreList+=p.getName();
			}
		}
		if(numUnhappy == 0) {
			return "";
		}
		else {
			return unhappyPlants;
		}
	}
	
	/***
	 * Handles the event by setting the Scene for Garden Report and displaying it as pop-up window
	 * @param e the ActionEvent for "Generate Garden Report" button
	 */
	public void handleGenerateReport(ActionEvent e) {
		Stage popUp = viewTwo.makePopUpForReport();
		String report = generateReportText();
		String unhappy = getUnhappyPlants();
		FlowPane pane = viewTwo.makeReportPane(report, unhappy);
		Scene popScene = new Scene(pane);
        popUp.setScene(popScene);
        popUp.show();
	}

	public ViewTwo getViewTwo() {
		// TODO Auto-generated method stub
		return viewTwo;
	}
}
