package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
		Scene scene = applicationContext.getBean("explorerScene", Scene.class);
		stage.setScene(scene);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeExplorer(stage, scene);
		
		stage.show();
	}
}