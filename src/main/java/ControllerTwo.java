import java.io.File;

import java.io.FileOutputStream;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerTwo {
	ViewTwo view2;
	ModelTwo model;
	FileChooser fileChooserSave;
	File fileToSave;
	int identifier;
	
	public ControllerTwo() {
		fileChooserSave = new FileChooser();
		identifier = 0;
	}
	
	public void setViewTwo(ViewTwo v2) {
		this.view2 = v2;
	}
	
	public void setModelTwo(ModelTwo m2) {
		this.model = m2;
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
			case "large": iv.setFitHeight(130);
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
		for(PlantImageView p : view2.plantsInGarden) {
			gard.add(p.plant);
		}
		System.out.print(gard);
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
		model.garden.setGardensPlants(updateGarden());
	}	
	
	public void enter(ViewTwo view, MouseEvent event, PlantImageView v) {
		if(v.getPaneLoc().equals("grid")) {
			setHandlerForDrag(v, view);
			view.fp.getChildren().add(v);
			view.sideView.remove(v);
			handleReplaceImgView(view,view.gp, v);
			v.plant.id = identifier;
			view.plantsInGarden.add(v);
			setHandlerDeletePlant(v, view);
			identifier++;
		}
	}
	
	public void setHandlerDeletePlant(PlantImageView iv1, ViewTwo view) {
		iv1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                MouseButton button = event.getButton();
                if(button==MouseButton.SECONDARY){
                	view.plantsInGarden.remove(iv1);
                	view.plantsInWasteBasket.add(iv1);
                	String plantWaste = "";
                	for (PlantImageView p:view.plantsInWasteBasket) {
            			plantWaste += p.plant.name + ", ";
            		}
                	Tooltip.install(view.wasteBasket, new Tooltip(plantWaste +"\n"));
                	iv1.setImage(null);
                }
                model.garden.setGardensPlants(updateGarden());
            }
        });
	}
	
	public void setHandlerForDrag(PlantImageView iv1, ViewTwo view) {
		iv1.setOnMouseDragged(event -> drag(view, event, iv1));
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
	
	public void handleNewButtonPress(ActionEvent e) {
		System.out.println("newButton press");
		view2 = new ViewTwo(view2.stage, model.garden.getBg());
	}

	public void handleSaveButton(Stage stage) {
		fileChooserSave.setTitle("Save Your Garden");
		fileChooserSave.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized File(*.ser)", "*.ser"));
		fileToSave = fileChooserSave.showSaveDialog(stage);
		if(!fileToSave.getName().contains(".")) {
			fileToSave = new File(fileToSave.getAbsolutePath() + ".ser");
	    }
		serializeGarden(fileToSave);
	}
	public void handleGSoilButton(ActionEvent event){
		Stage popUp = view2.makePopUpForSunSoil("soil");
        popUp.show();
        FlowPane pane = view2.addButtonsToSoilPopUp();
        for(Node n : pane.getChildren())
        	switch((String) n.getUserData()) {
	        	case "All":
			        ((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenSoil="all";
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
	
	public void handleLightButton(ActionEvent e){
		Stage popUp = view2.makePopUpForSunSoil("light");
        FlowPane pane = view2.addButtonsToLightPopUp();
        
        for(Node n : pane.getChildren())
        	switch((String) n.getUserData()) {
	        	case "All":
			        ((Button) n).setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					      model.garden.gardenLight="all";
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
					      System.out.println(model.garden.gardenLight);
					    }
					});;
        	}

        Scene popScene = new Scene(pane);
        popUp.setScene(popScene);
        popUp.show();
	}
	
	public int generateScore() {
		double goodChoice = 1;
		double overallNumber = 1;
		for(Plant p : model.garden.gardensPlants) {
			overallNumber++;
				if((p.plantSoil.equals(model.garden.gardenSoil) | model.garden.gardenSoil.equals("all"))| 
						(p.plantLight.equals(model.garden.gardenLight) | model.garden.gardenLight.equals("all"))) {
					goodChoice++;
				}
				else if(!((p.plantSoil.equals(model.garden.gardenSoil) | model.garden.gardenSoil.equals("all")))| 
					(p.plantLight.equals(model.garden.gardenLight) | model.garden.gardenLight.equals("all"))) {
					goodChoice+=.65;
				}
				else if(((p.plantSoil.equals(model.garden.gardenSoil) | model.garden.gardenSoil.equals("all")))| 
						!(p.plantLight.equals(model.garden.gardenLight) | model.garden.gardenLight.equals("all"))) {
					goodChoice+=.65;
				}
				else {
					goodChoice+=.05;
				}
		}
		double total = goodChoice / overallNumber;
		int score = (int) (total*100);
		return score;
	}
	
	public String generateReportText() {
		String report = "";
		int score = 0;
		ArrayList<Plant> arrayCopy = view2.model.garden.gardensPlants;
		ArrayList<String> ignoreList = new ArrayList<String>();
		boolean ignore = false;
		if(arrayCopy.isEmpty()) {
			report += "There's nothing in the garden";
		}
		else {
			report += "There are ";
		}
		for(Plant p : arrayCopy) {
			System.out.print(p.name+"   ");
			System.out.print(ignoreList+"\n");
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
	
	public void handleGenerateReport(ActionEvent e) {
		Stage popUp = view2.makePopUpForReport();
		String report = generateReportText();
		FlowPane pane = view2.makeReportPane(report);
		Scene popScene = new Scene(pane);
        popUp.setScene(popScene);
        popUp.show();
	}
}
