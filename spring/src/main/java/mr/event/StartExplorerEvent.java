package mr.event;

import mr.client.Client;
import org.springframework.context.ApplicationEvent;

public class StartExplorerEvent extends ApplicationEvent
{
	private final Client client;
	private final String status;
	
	public StartExplorerEvent(Object source, Client client, String status)
	{
		super(source);
		
		this.client = client;
		this.status = status;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public String getStatus()
	{
		return status;
	}
}