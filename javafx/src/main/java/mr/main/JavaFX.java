package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.explorer.ExplorerController;
import mr.explorer.IconLoader;
import mr.explorer.ResourcesIconLoader;
import mr.explorer.StageExplorerController;
import mr.scene.FxmlSceneFactory;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
import mr.stage.SimpleStageInitializer;
import mr.stage.StageInitializer;

public class JavaFX extends Application
{
	public static void main(String[] args)
	{
		Application.launch(JavaFX.class, args);
	}
	
	@Override
	public void start(Stage stage) throws SceneFactoryException
	{
		SceneFactory sceneFactory = new FxmlSceneFactory("layout.fxml");
		IconLoader iconLoader = new ResourcesIconLoader();
		ExplorerController explorerController = new StageExplorerController(iconLoader);
		Scene scene = sceneFactory.create("explorer", explorerController);
		
		StageInitializer stageInitializer = new SimpleStageInitializer("MrFTP", scene);
		stageInitializer.initialize(stage);
		
		stage.show();
	}
}