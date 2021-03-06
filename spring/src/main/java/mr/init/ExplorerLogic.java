package mr.init;

import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.event.ClientChangedEvent;
import mr.event.LocalEntriesViewRefreshEvent;
import mr.event.RemoteEntriesViewRefreshEvent;
import mr.event.StartExplorerEvent;
import mr.event.StartLauncherEvent;
import mr.explorer.ExplorerController;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("explorerScene")
@RequiredArgsConstructor
public class ExplorerLogic implements ApplicationListener<StartExplorerEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final ExplorerController explorerController;
	
	@Override
	public void onApplicationEvent(StartExplorerEvent startExplorerEvent)
	{
		Client client = startExplorerEvent.getClient();
		ClientChangedEvent clientChangedEvent = new ClientChangedEvent(this, client);
		String status = startExplorerEvent.getStatus();
		
		applicationEventPublisher.publishEvent(clientChangedEvent);
		
		refreshEntryViews(client);
		initializeExplorerController(client, status);
	}
	
	private void refreshEntryViews(Client client)
	{
		RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent = new RemoteEntriesViewRefreshEvent(this, client);
		LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent = new LocalEntriesViewRefreshEvent(this);
		
		applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
		applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
	}
	
	private void initializeExplorerController(Client client, String status)
	{
		explorerController.setOnClose(() -> {
			StartLauncherEvent startLauncherEvent = new StartLauncherEvent(this);
			
			client.close();
			applicationEventPublisher.publishEvent(startLauncherEvent);
		});
		
		explorerController.setOnRefresh(() -> {
			refreshEntryViews(client);
		});
		
		explorerController.setStatus(status);
	}
}