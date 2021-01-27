package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.explorer.SimpleExplorerService;
import mr.stage.StageInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Explorer extends Application
{
	private final ApplicationContext applicationContext;
	
	public static void main(String[] args)
	{
		Application.launch(Explorer.class, args);
	}
	
	public Explorer()
	{
		applicationContext = new AnnotationConfigApplicationContext("mr.init");
	}
	
	@Override
	public void start(Stage stage)
	{
		initializeExplorerService(stage);
		startExplorer(stage);
	}
	
	private void initializeExplorerService(Stage stage)
	{
		SimpleExplorerService simpleExplorerService = applicationContext.getBean(SimpleExplorerService.class);
		
		simpleExplorerService.setOnClose(() -> {
			startLauncher();
			stage.close();
		});
		
		simpleExplorerService.setOnRefresh(() -> {
		
		});
	}
	
	private void startExplorer(Stage stage)
	{
		Scene scene = applicationContext.getBean("explorerScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeExplorer(stage, scene);
		
		stage.show();
	}
	
	private void startLauncher()
	{
		Stage stage = new Stage();
		Scene scene = applicationContext.getBean("launcherScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeLauncher(stage, scene);
		
		stage.show();
	}
}