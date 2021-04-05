package mr.event;

import org.springframework.context.ApplicationEvent;

public class LocalEntriesViewRefreshEvent extends ApplicationEvent
{
	public LocalEntriesViewRefreshEvent(Object source)
	{
		super(source);
	}
}