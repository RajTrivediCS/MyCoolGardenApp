package Application;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewsContainer {
	Map<ViewName,Scene> sceneMap;
	public ViewsContainer(Stage stage) {
		sceneMap = new HashMap<ViewName,Scene>();
		sceneMap.put(ViewName.SCENE1, new ViewOne(stage).getScene());
		sceneMap.put(ViewName.SCENE2, new ViewTwo(stage).getScene());
	}
	public Map<ViewName, Scene> getSceneMap() {
		return sceneMap;
	}

}
