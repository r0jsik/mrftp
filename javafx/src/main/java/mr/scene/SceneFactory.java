package mr.scene;

import javafx.scene.Scene;

public interface SceneFactory
{
	Scene create(String sceneName, Object controller) throws SceneFactoryException;
}