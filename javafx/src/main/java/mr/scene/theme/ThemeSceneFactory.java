package mr.scene.theme;

import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;

@RequiredArgsConstructor
public class ThemeSceneFactory implements SceneFactory
{
	private final SceneFactory sceneFactory;
	private final Theme theme;
	
	@Override
	public Scene create(String sceneName, Object controller) throws SceneFactoryException
	{
		Scene scene = sceneFactory.create(sceneName, controller);
		theme.stylize(scene);
		
		return scene;
	}
}