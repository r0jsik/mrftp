package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.ftp.ApacheClientFactory;
import mr.ftp.ClientFactory;
import mr.launcher.ClientLauncherService;
import mr.launcher.LauncherController;
import mr.launcher.LauncherService;
import mr.launcher.StageLauncherController;
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
		ClientFactory clientFactory = new ApacheClientFactory();
		
		LauncherService launcherService = new ClientLauncherService(clientFactory, client -> {
			// replace scene and show files from client
		}, exception -> {
			// show warning
		});
		
		LauncherController launcherController = new StageLauncherController(launcherService);
		Scene scene = sceneFactory.create("launcher", launcherController);
		
		StageInitializer stageInitializer = new SimpleStageInitializer("MrFTP", scene);
		stageInitializer.initialize(stage);
		
		// read data from configuration
		launcherController.setHostname("localhost");
		launcherController.setPort(21);
		
		stage.show();
	}
}