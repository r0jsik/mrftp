package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.client.Client;
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
		Scene scene = applicationContext.getBean("launcherScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeLauncher(stage, scene);
		
		SimpleLauncherService simpleLauncherService = applicationContext.getBean(SimpleLauncherService.class);
		
		simpleLauncherService.setOnSuccess(client -> {
			stageInitializer.initializeExplorer(stage, scene);
			start(client);
		});
		
		simpleLauncherService.setOnFailure(exception -> {
			exception.printStackTrace();
			stage.close();
		});
		
		stage.show();
	}
	
	private void start(Client client)
	{
	
	}
}