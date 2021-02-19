package mr.event;

import org.springframework.context.ApplicationEvent;

public class StartLauncherEvent extends ApplicationEvent
{
	public StartLauncherEvent(Object source)
	{
		super(source);
	}
}