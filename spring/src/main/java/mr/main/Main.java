package mr.main;

import javafx.application.Application;
import javafx.stage.Stage;
import mr.event.StartLauncherEvent;
import mr.init.StageLogic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application
{
	private final ApplicationContext applicationContext;
	
	public static void main(String[] args)
	{
		Application.launch(Main.class, args);
	}
	
	public Main()
	{
		applicationContext = new AnnotationConfigApplicationContext("mr.init");
	}
	
	@Override
	public void start(Stage stage)
	{
		StageLogic stageLogic = applicationContext.getBean(StageLogic.class);
		stageLogic.setStage(stage);
		
		StartLauncherEvent startLauncherEvent = new StartLauncherEvent(this);
		applicationContext.publishEvent(startLauncherEvent);
	}
}