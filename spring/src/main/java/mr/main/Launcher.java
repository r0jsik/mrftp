package mr.main;

import javafx.application.Application;
import javafx.stage.Stage;
import mr.event.StartLauncherEvent;
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
		StartLauncherEvent startLauncherEvent = new StartLauncherEvent(this, stage);
		applicationContext.publishEvent(startLauncherEvent);
	}
}