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
		applicationContext = new AnnotationConfigApplicationContext(StageConfiguration.class, ExplorerConfiguration.class);
	}
	
	@Override
	public void start(Stage stage)
	{
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initialize(stage);
		
		Scene scene = applicationContext.getBean(Scene.class);
		stage.setScene(scene);
		
		stage.setMinWidth(640);
		stage.setMinHeight(480);
		stage.setWidth(640);
		stage.setHeight(480);
		stage.show();
		
		LocalEntriesContext localEntriesContext = applicationContext.getBean(LocalEntriesContext.class);
		localEntriesContext.initialize();
	}
}