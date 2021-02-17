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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LocalEntriesControllerLogic implements InitializingBean, ApplicationListener<ClientChangedEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final EntriesController localEntriesController;
	private final Walk remoteWalk;
	private final Walk localWalk;
	
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
			
			remove(localPath);
			applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
		});
	}
	
	private void remove(String localPath)
	{
		File file = new File(localPath);
		file.delete();
	}
	
	@Override
	public void onApplicationEvent(ClientChangedEvent clientChangedEvent)
	{
		Client client = clientChangedEvent.getClient();
		RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent = new RemoteEntriesViewRefreshEvent(this, client);
		LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent = new LocalEntriesViewRefreshEvent(this);
		
		localEntriesController.setOnEntryTransmitted(entry -> {
			String remotePath = remoteWalk.resolve(entry);
			String localPath = localWalk.resolve(entry);
			
			upload(client, remotePath, localPath);
			
			applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
			applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
		});
	}
	
	private void upload(Client client, String remotePath, String localPath)
	{
		try
		{
			File file = new File(localPath);
			FileInputStream fileInputStream = new FileInputStream(file);
			
			client.upload(remotePath, fileInputStream);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
}