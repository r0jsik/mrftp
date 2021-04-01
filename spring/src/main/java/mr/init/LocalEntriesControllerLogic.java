package mr.init;

import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.entry.EntriesController;
import mr.event.ClientChangedEvent;
import mr.event.LocalEntriesViewRefreshEvent;
import mr.event.RemoteEntriesViewRefreshEvent;
import mr.walk.Walk;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalEntriesControllerLogic implements InitializingBean, ApplicationListener<ClientChangedEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final EntriesController localEntriesController;
	private final Walk remoteWalk;
	private final Walk localWalk;
	private final Client localClient;
	
	@Override
	public void afterPropertiesSet()
	{
		LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent = new LocalEntriesViewRefreshEvent(this);
		
		localEntriesController.setOnEntryOpened(entry -> {
			localWalk.to(entry);
			applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
		});
		
		localEntriesController.setOnEntryDeleted(entry -> {
			String localPath = localWalk.resolve(entry);
			
			localClient.remove(localPath);
			applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
		});
	}
	
	@Override
	public void onApplicationEvent(ClientChangedEvent clientChangedEvent)
	{
		Client client = clientChangedEvent.getClient();
		RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent = new RemoteEntriesViewRefreshEvent(this, client);
		LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent = new LocalEntriesViewRefreshEvent(this);
		
		localEntriesController.setOnEntryTransmitted(entry -> {
			upload(client, entry);
			
			applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
			applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
		});
	}
	
	private void upload(Client client, String entry)
	{
		String from = localWalk.toString();
		
		localClient.walk(from, "/" + entry, (relativePath, isDirectory) -> {
			tryToUpload(client, relativePath, isDirectory);
		});
	}
	
	private void tryToUpload(Client client, String relativePath, boolean isDirectory)
	{
		if ( !isDirectory)
		{
			String remotePath = remoteWalk.resolve(relativePath);
			String localPath = localWalk.resolve(relativePath);
			
			localClient.read(localPath, inputStream -> {
				client.write(remotePath, inputStream::transferTo);
			});
		}
	}
}