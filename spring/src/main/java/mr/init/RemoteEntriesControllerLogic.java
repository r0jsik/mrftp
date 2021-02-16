package mr.init;

import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.entry.EntriesController;
import mr.event.ClientChangedEvent;
import mr.event.RemoteEntriesViewRefreshEvent;
import mr.walk.Walk;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class RemoteEntriesControllerLogic implements ApplicationListener<ClientChangedEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final EntriesController remoteEntriesController;
	private final Walk remoteWalk;
	private final Walk localWalk;
	
	@Override
	public void onApplicationEvent(ClientChangedEvent clientChangedEvent)
	{
		Client client = clientChangedEvent.getClient();
		RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent = new RemoteEntriesViewRefreshEvent(this, client);
		
		remoteEntriesController.setOnEntryOpened(entry -> {
			remoteWalk.to(entry);
			applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
		});
		
		remoteEntriesController.setOnEntryTransmitted(entry -> {
			usingPathsWithAppendedEntry(entry, (remotePath, localPath) -> {
				download(client, remotePath, localPath);
				applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
			});
		});
		
		remoteEntriesController.setOnEntryDeleted(entry -> {
			usingPathsWithAppendedEntry(entry, (remotePath, localPath) -> {
				remove(client, remotePath);
				applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
			});
		});
	}
	
	private void usingPathsWithAppendedEntry(String entry, BiConsumer<String, String> callback)
	{
		String remotePath = remoteWalk.toString() + "/" + entry;
		String localPath = localWalk.toString() + "/" + entry;
		
		callback.accept(remotePath, localPath);
	}
	
	private void download(Client client, String remotePath, String localPath)
	{
		try
		{
			File file = new File(localPath);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			
			client.download(remotePath, fileOutputStream);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	private void remove(Client client, String remotePath)
	{
		try
		{
			client.remove(remotePath);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
}