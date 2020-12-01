import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlantImageView extends ImageView{
	String paneLocation;
	Plant plant;
	
	public PlantImageView(Plant plant){
		super();
		this.setPickOnBounds(true);
		this.plant = plant;
	}
	
	public PlantImageView(Image im){
		super(im);
		this.setPickOnBounds(true);
	}
	
	public void setPaneLoc(String s) {
		this.paneLocation = s;
	}
	public String getPaneLoc() {
		return this.paneLocation;
	}
}
