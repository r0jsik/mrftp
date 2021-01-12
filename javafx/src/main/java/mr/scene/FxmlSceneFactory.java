package mr.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URL;

@RequiredArgsConstructor
public class FxmlSceneFactory implements SceneFactory
{
	private final String fileName;
	
	@Override
	public Scene create(String sceneName, Object controller) throws SceneFactoryException
	{
		try
		{
			URL location = getLocation(sceneName);
			FXMLLoader fxmlLoader = getLoader(location, controller);
			Parent root = fxmlLoader.load();
			
			return new Scene(root);
		}
		catch (IOException | IllegalStateException exception)
		{
			throw new SceneFactoryException();
		}
	}
	
	private URL getLocation(String sceneName)
	{
		return getClass().getResource(String.join("", "/", sceneName, "/", fileName));
	}
	
	private FXMLLoader getLoader(URL location, Object controller)
	{
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setController(controller);
		fxmlLoader.setLocation(location);
		
		return fxmlLoader;
	}
}