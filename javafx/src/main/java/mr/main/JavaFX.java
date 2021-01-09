package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.scene.FxmlSceneFactory;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
import mr.stage.SimpleStageInitializer;
import mr.stage.StageInitializer;

public class JavaFX extends Application
{
	private final StageInitializer stageInitializer;
	
	public static void main(String[] args)
	{
		Application.launch(JavaFX.class, args);
	}
	
	public JavaFX() throws SceneFactoryException
	{
		SceneFactory sceneFactory = new FxmlSceneFactory("layout.fxml");
		Object controller = new Object();
		Scene scene = sceneFactory.create("main", controller);
		
		stageInitializer = new SimpleStageInitializer("MrFTP", scene);
	}
	
	@Override
	public void start(Stage stage)
	{
		stageInitializer.initialize(stage);
		stage.show();
	}
}