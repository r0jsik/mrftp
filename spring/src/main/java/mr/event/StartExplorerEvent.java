package mr.event;

import javafx.stage.Stage;
import mr.client.Client;
import org.springframework.context.ApplicationEvent;

public class StartExplorerEvent extends ApplicationEvent
{
	private final Client client;
	private final Stage stage;
	
	public StartExplorerEvent(Object source, Client client, Stage stage)
	{
		super(source);
		
		this.client = client;
		this.stage = stage;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public Stage getStage()
	{
		return stage;
	}
}