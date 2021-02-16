package mr.init;

import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.event.ClientChangedEvent;
import mr.event.LocalEntriesViewRefreshEvent;
import mr.event.RemoteEntriesViewRefreshEvent;
import mr.explorer.ExplorerController;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExplorerControllerLogic implements ApplicationListener<ClientChangedEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final ExplorerController explorerController;
	
	@Override
	public void onApplicationEvent(ClientChangedEvent clientChangedEvent)
	{
		refresh(clientChangedEvent);
		
		explorerController.setOnRefresh(() -> {
			refresh(clientChangedEvent);
		});
	}
	
	private void refresh(ClientChangedEvent clientChangedEvent)
	{
		Client client = clientChangedEvent.getClient();
		RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent = new RemoteEntriesViewRefreshEvent(this, client);
		LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent = new LocalEntriesViewRefreshEvent(this);
		
		applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
		applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
	}
}