package mr.event;

import mr.client.Client;
import org.springframework.context.ApplicationEvent;

public class ClientChangedEvent extends ApplicationEvent
{
	private final Client client;
	
	public ClientChangedEvent(Object source, Client client)
	{
		super(source);
		
		this.client = client;
	}
	
	public Client getClient()
	{
		return client;
	}
}