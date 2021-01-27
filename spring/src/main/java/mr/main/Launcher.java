package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.launcher.SimpleLauncherService;
import mr.stage.StageInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Launcher extends Application
{
	private final ApplicationContext applicationContext;
	
	public static void main(String[] args)
	{
		Application.launch(Launcher.class, args);
	}
	
	public Launcher()
	{
		applicationContext = new AnnotationConfigApplicationContext("mr.init");
	}
	
	@Override
	public void start(Stage stage)
	{
		initializeLauncherService(stage);
		startLauncher(stage);
	}
	
	private void initializeLauncherService(Stage stage)
	{
		SimpleLauncherService simpleLauncherService = applicationContext.getBean(SimpleLauncherService.class);
		
		simpleLauncherService.setOnSuccess(client -> {
			startExplorer();
			stage.close();
		});
		
		simpleLauncherService.setOnFailure(exception -> {
			exception.printStackTrace();
			stage.close();
		});
	}
	
	private void startLauncher(Stage stage)
	{
		Scene scene = applicationContext.getBean("launcherScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeLauncher(stage, scene);
		
		stage.show();
	}
	
	private void startExplorer()
	{
		Stage stage = new Stage();
		Scene scene = applicationContext.getBean("explorerScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeExplorer(stage, scene);
		
		stage.show();
	}
}