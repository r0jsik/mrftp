package mr.init;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.event.ClientChangedEvent;
import mr.event.LocalEntriesViewRefreshEvent;
import mr.event.RemoteEntriesViewRefreshEvent;
import mr.event.StartExplorerEvent;
import mr.event.StartLauncherEvent;
import mr.explorer.ExplorerController;
import mr.stage.StageInitializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ExplorerLogic implements InitializingBean, ApplicationListener<StartExplorerEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final ExplorerController explorerController;
	private final Scene explorerScene;
	private final StageInitializer stageInitializer;
	
	@Override
	public void afterPropertiesSet()
	{
		initializeLabels();
	}
	
	private void initializeLabels()
	{
		explorerController.setCloseLabel("Zamknij");
		explorerController.setRefreshLabel("Odśwież");
	}
	
	@Override
	public void onApplicationEvent(StartExplorerEvent startExplorerEvent)
	{
		Client client = startExplorerEvent.getClient();
		ClientChangedEvent clientChangedEvent = new ClientChangedEvent(this, client);
		Stage stage = startExplorerEvent.getStage();
		String status = startExplorerEvent.getStatus();
		
		applicationEventPublisher.publishEvent(clientChangedEvent);
		
		refreshEntryViews(client);
		initializeExplorerController(client, stage, status);
		showExplorer(stage);
	}
	
	private void refreshEntryViews(Client client)
	{
		RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent = new RemoteEntriesViewRefreshEvent(this, client);
		LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent = new LocalEntriesViewRefreshEvent(this);
		
		applicationEventPublisher.publishEvent(remoteEntriesViewRefreshEvent);
		applicationEventPublisher.publishEvent(localEntriesViewRefreshEvent);
	}
	
	private void initializeExplorerController(Client client, Stage stage, String status)
	{
		explorerController.setOnClose(() -> {
			close(client);
			
			StartLauncherEvent startLauncherEvent = new StartLauncherEvent(this, new Stage());
			applicationEventPublisher.publishEvent(startLauncherEvent);
			
			stage.close();
		});
		
		explorerController.setOnRefresh(() -> {
			refreshEntryViews(client);
		});
		
		explorerController.setStatus(status);
	}
	
	private void close(Client client)
	{
		try
		{
			client.close();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	private void showExplorer(Stage stage)
	{
		stageInitializer.initializeExplorer(stage, explorerScene);
		stage.show();
	}
}