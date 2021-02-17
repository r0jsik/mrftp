package mr.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StartLauncherEvent extends ApplicationEvent
{
	private final Stage stage;
	
	public StartLauncherEvent(Object source, Stage stage)
	{
		super(source);
		
		this.stage = stage;
	}
	
	public Stage getStage()
	{
		return stage;
	}
}