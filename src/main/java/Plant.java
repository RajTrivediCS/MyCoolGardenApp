
/***
 * 
 * @author Raj Trivedi, Noah Hodgson, Luis Figueroa
 *
 */
public class Plant implements java.io.Serializable {
	private String name;
	private double xLoc;
	private double yLoc;
	private String plantLight;
	private String plantSoil;
	private String plantSize;
	private int id;
	
	/***
	 * Initializes the instance variables of Plant with the given parameters
	 * @param name the name of plant
	 * @param xLoc the x coordinate of plant
	 * @param yLoc the y coordinate of plant
	 */
	public Plant(String name, double xLoc, double yLoc){
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		//make another constructor in the future
	}
	
	/***
	 * Initializes the instance variables of Plant with the given parameters
	 * 
	 * @param name the name of plant
	 * @param xLoc the x coordinate of plant
	 * @param yLoc the y coordinate of plant
	 * @param pLight the amount of light for plant
	 * @param pSoil the amount of soil for plant
	 * @param pSize the size of plant
	 */
	public Plant(String name, double xLoc, double yLoc, String pLight, String pSoil, String pSize){
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.plantLight = pLight;
		this.plantSoil = pSoil;
		this.plantSize = pSize;
	}
	
	/***
	 * Updates the location of plant from the given x and y coordinates
	 * @param gX the x coordinate to update
	 * @param gY the y coordinate to update
	 */
	public void updatePlantLocation(double gX, double gY) {
		xLoc = gX;
		yLoc = gY;
	}
	
	/***
	 * Returns x coordinate of plant
	 * @return the current x coordinate
	 */
	public double getXLoc() {
		return this.xLoc;
	}

	/***
	 * Returns y coordinate of plant
	 * @return the current y coordinate
	 */
	public double getYLoc() {
		return this.yLoc;
	}
	
	/***
	 * Sets the x coordinate from the given value
	 * @param d the x coordinate to set
	 */
	public void setXLoc(double d) {
		this.xLoc = d;
	}
	
	/***
	 * Sets the y coordinate from the given value
	 * @param d the y coordinate to set
	 */
	public void setYLoc(double d) {
		this.yLoc = d;
	}
	
	/***
	 * Returns the name of plant
	 */
	public String toString() {
		return name;
	}

	public int getID() {
		return id;
	}

	public String getPlantSize() {
		return plantSize;
	}

	public void setID(int identifier) {
		this.id = identifier;
	}

	public String getPlantLight() {
		return plantLight;
	}

	public String getPlantSoil() {
		return plantSoil;
	}

	public String getName() {
		return name;
	}
}
