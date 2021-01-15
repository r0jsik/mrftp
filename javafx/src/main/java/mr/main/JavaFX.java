package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.scene.FxmlSceneFactory;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
import mr.stage.SimpleStageInitializer;
import mr.stage.StageInitializer;
import mr.explorer.StageExplorerController;
import mr.explorer.ExplorerController;

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
		ExplorerController explorerController = new StageExplorerController();
		Scene scene = sceneFactory.create("explorer", explorerController);
		
		StageInitializer stageInitializer = new SimpleStageInitializer("MrFTP", scene);
		stageInitializer.initialize(stage);
		
		explorerController.showRemote(() -> "Example file 1");
		explorerController.showRemote(() -> "Example file 2");
		explorerController.showLocal(() -> "Example file 3");
		explorerController.showLocal(() -> "Example file 4");
		
		stage.show();
	}
}