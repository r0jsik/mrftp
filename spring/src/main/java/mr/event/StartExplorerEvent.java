package mr.event;

import javafx.stage.Stage;
import mr.client.Client;
import org.springframework.context.ApplicationEvent;

public class StartExplorerEvent extends ApplicationEvent
{
	private final Client client;
	private final Stage stage;
	private final String status;
	
	public StartExplorerEvent(Object source, Client client, Stage stage, String status)
	{
		super(source);
		
		this.client = client;
		this.stage = stage;
		this.status = status;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public Stage getStage()
	{
		return stage;
	}
	
	public String getStatus()
	{
		return status;
	}
}