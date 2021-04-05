package mr.event;

import mr.client.Client;
import mr.entry.EntriesProjector;
import org.springframework.context.ApplicationEvent;

public class RemoteEntriesViewRefreshEvent extends ApplicationEvent
{
	private final Client client;
	
	public RemoteEntriesViewRefreshEvent(Object source, Client client)
	{
		super(source);
		
		this.client = client;
	}
	
	public EntriesProjector getEntriesProjector()
	{
		return client.entriesProjector();
	}
}