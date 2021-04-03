package mr.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class FxmlSceneFactory implements SceneFactory
{
	private final String layoutFileName;
	private final String labelsFileName;
	
	@Override
	public Scene create(String sceneName, Object controller)
	{
		try
		{
			ResourceBundle resources = getLabelsBundle(sceneName);
			URL location = getLocation(sceneName);
			FXMLLoader fxmlLoader = getLoader(location, controller);
			fxmlLoader.setResources(resources);
			Parent root = fxmlLoader.load();
			
			return new Scene(root);
		}
		catch (IOException | IllegalStateException exception)
		{
			throw new SceneFactoryException(exception);
		}
	}
	
	private ResourceBundle getLabelsBundle(String sceneName)
	{
		String labelsFilePath = String.join("/", sceneName, labelsFileName);
		Locale locale = Locale.getDefault();
		
		return ResourceBundle.getBundle(labelsFilePath, locale);
	}
	
	private URL getLocation(String sceneName)
	{
		String layoutFilePath = String.join("/", "", sceneName, layoutFileName);
		URL location = getClass().getResource(layoutFilePath);
		
		return location;
	}
	
	private FXMLLoader getLoader(URL location, Object controller)
	{
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setController(controller);
		fxmlLoader.setLocation(location);
		
		return fxmlLoader;
	}
}