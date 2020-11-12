package MultipleScenesHandler;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneContainer {
	Map<SceneName,Scene> sceneMap;
	public SceneContainer(Stage stage) {
		sceneMap = new HashMap<SceneName,Scene>();
		sceneMap.put(SceneName.SCENE1, new SceneOne(stage).getScene());
		sceneMap.put(SceneName.SCENE2, new SceneTwo(stage).getScene());
	}
	public Map<SceneName, Scene> getSceneMap() {
		return sceneMap;
	}

}
