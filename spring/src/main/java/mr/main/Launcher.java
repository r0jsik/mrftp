package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.client.Client;
import mr.entry.EntriesController;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.explorer.CallbackExplorerService;
import mr.launcher.CallbackLauncherService;
import mr.stage.StageInitializer;
import mr.transmitter.Transmitter;
import mr.walk.Walk;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.BiConsumer;

public class Launcher extends Application
{
	private final ApplicationContext applicationContext;
	
	public static void main(String[] args)
	{
		Application.launch(Launcher.class, args);
	}
	
	public Launcher()
	{
		applicationContext = new AnnotationConfigApplicationContext("mr.init");
	}
	
	@Override
	public void start(Stage stage)
	{
		initializeLocalEntriesController();
		startLauncher(stage);
	}
	
	private void initializeLocalEntriesController()
	{
		EntriesController entriesController = applicationContext.getBean("localEntriesController", EntriesController.class);
		
		entriesController.setOnEntryOpened(entry -> {
			Walk walk = applicationContext.getBean("localWalk", Walk.class);
			walk.to(entry);
			
			refreshLocalEntriesView();
		});
	}
	
	private void refreshLocalEntriesView()
	{
		Walk walk = applicationContext.getBean("localWalk", Walk.class);
		
		EntriesView entriesView = applicationContext.getBean("localEntriesView", EntriesView.class);
		entriesView.hideAll();
		
		try
		{
			EntriesProjector entriesProjector = applicationContext.getBean("localEntriesProjector", EntriesProjector.class);
			entriesProjector.show(walk.toString(), entriesView);
		}
		catch (EntriesProjectionException exception)
		{
			exception.printStackTrace();
		}
	}
	
	private void startLauncher(Stage stage)
	{
		initializeLauncherService(stage);
		showLauncher(stage);
	}
	
	private void initializeLauncherService(Stage stage)
	{
		CallbackLauncherService callbackLauncherService = applicationContext.getBean(CallbackLauncherService.class);
		
		callbackLauncherService.setOnSuccess(client -> {
			startExplorer(client, new Stage());
			stage.close();
		});
		
		callbackLauncherService.setOnFailure(exception -> {
			exception.printStackTrace();
			stage.close();
		});
	}
	
	private void showLauncher(Stage stage)
	{
		Scene scene = applicationContext.getBean("launcherScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeLauncher(stage, scene);
		
		stage.show();
	}
	
	private void startExplorer(Client client, Stage stage)
	{
		initializeExplorerService(client, stage);
		initializeRemoteEntriesController(client);
		initializeLocalEntriesController(client);
		refreshRemoteEntriesView(client);
		refreshLocalEntriesView();
		showExplorer(stage);
	}
	
	private void initializeExplorerService(Client client, Stage stage)
	{
		CallbackExplorerService callbackExplorerService = applicationContext.getBean(CallbackExplorerService.class);
		
		callbackExplorerService.setOnRefresh(() -> {
			refreshRemoteEntriesView(client);
			refreshLocalEntriesView();
		});
		
		callbackExplorerService.setOnClose(() -> {
			// client.close()
			startLauncher(new Stage());
			stage.close();
		});
	}
	
	private void refreshRemoteEntriesView(Client client)
	{
		Walk walk = applicationContext.getBean("remoteWalk", Walk.class);
		
		EntriesView entriesView = applicationContext.getBean("remoteEntriesView", EntriesView.class);
		entriesView.hideAll();
		
		try
		{
			EntriesProjector entriesProjector = client.entriesProjector();
			entriesProjector.show(walk.toString(), entriesView);
		}
		catch (EntriesProjectionException exception)
		{
			exception.printStackTrace();
		}
	}
	
	private void initializeRemoteEntriesController(Client client)
	{
		EntriesController entriesController = applicationContext.getBean("remoteEntriesController", EntriesController.class);
		
		entriesController.setOnEntryOpened(entry -> {
			Walk walk = applicationContext.getBean("remoteWalk", Walk.class);
			walk.to(entry);
			
			refreshRemoteEntriesView(client);
		});
		
		entriesController.setOnEntryTransmitted(entry -> {
			usingPathsWithAppendedEntry(entry, (remotePath, localPath) -> {
				Transmitter transmitter = applicationContext.getBean(Transmitter.class);
				transmitter.download(client, remotePath, localPath);
			});
		});
	}
	
	private void usingPathsWithAppendedEntry(String entry, BiConsumer<String, String> callback)
	{
		Walk remoteWalk = applicationContext.getBean("remoteWalk", Walk.class);
		String remotePath = remoteWalk.toString() + "/" + entry;
		
		Walk localWalk = applicationContext.getBean("localWalk", Walk.class);
		String localPath = localWalk.toString() + "/" + entry;
		
		callback.accept(remotePath, localPath);
	}
	
	private void initializeLocalEntriesController(Client client)
	{
		EntriesController entriesController = applicationContext.getBean("localEntriesController", EntriesController.class);
		
		entriesController.setOnEntryTransmitted(entry -> {
			usingPathsWithAppendedEntry(entry, (remotePath, localPath) -> {
				Transmitter transmitter = applicationContext.getBean(Transmitter.class);
				transmitter.upload(client, localPath, remotePath);
			});
		});
	}
	
	private void showExplorer(Stage stage)
	{
		Scene scene = applicationContext.getBean("explorerScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeExplorer(stage, scene);
		
		stage.show();
	}
}