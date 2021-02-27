package mr.init;

import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.entry.EntriesController;
import mr.event.ClientChangedEvent;
import mr.event.LocalEntriesViewRefreshEvent;
import mr.event.RemoteEntriesViewRefreshEvent;
import mr.walk.Walk;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoteEntriesControllerLogic implements ApplicationListener<ClientChangedEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final EntriesController remoteEntriesController;
	private final Walk remoteWalk;
	private final Walk localWalk;
	private final Client localClient;
	
	@Override
	public void onApplicationEvent(ClientChangedEvent clientChangedEvent)
	{
		Client client = clientChangedEvent.getClient();
		RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent = new RemoteEntriesViewRefreshEvent(this, client);
		LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent = new LocalEntriesViewRefreshEvent(this);
		
		remoteEntriesController.setOnEntryOpened(entry -> {
			remoteWalk.to(entry);
			applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
		});
		
		remoteEntriesController.setOnEntryTransmitted(entry -> {
			download(client, entry);
			
			applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
			applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
		});
		
		remoteEntriesController.setOnEntryDeleted(entry -> {
			String remotePath = remoteWalk.resolve(entry);
			
			client.remove(remotePath);
			applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
		});
	}
	
	private void download(Client client, String entry)
	{
		String from = remoteWalk.toString();
		
		client.walk(from, entry, relativePath -> {
			tryToDownload(client, relativePath);
		});
	}
	
	private void tryToDownload(Client client, String relativePath)
	{
		String remotePath = remoteWalk.resolve(relativePath);
		String localPath = localWalk.resolve(relativePath);
		
		client.read(remotePath, inputStream -> {
			localClient.write(localPath, inputStream::transferTo);
		});
	}
}