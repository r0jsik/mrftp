package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.client.Client;
import mr.entry.EntriesController;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.explorer.SimpleExplorerService;
import mr.launcher.SimpleLauncherService;
import mr.stage.StageInitializer;
import mr.transmitter.Transmitter;
import mr.walk.Walk;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
		startLauncher(stage);
	}
	
	private void startLauncher(Stage stage)
	{
		initializeLocalEntriesController();
		
		initializeLauncherService(stage);
		showLauncher(stage);
	}
	
	private void initializeLocalEntriesController()
	{
		EntriesController entriesController = applicationContext.getBean("localEntriesController", EntriesController.class);
		Walk walk = applicationContext.getBean("localWalk", Walk.class);
		
		entriesController.setOnEntryOpened(entry -> {
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
	
	private void initializeLauncherService(Stage stage)
	{
		SimpleLauncherService simpleLauncherService = applicationContext.getBean(SimpleLauncherService.class);
		
		simpleLauncherService.setOnSuccess(client -> {
			startExplorer(client, new Stage());
			stage.close();
		});
		
		simpleLauncherService.setOnFailure(exception -> {
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
	
	private void initializeLocalEntriesController(Client client)
	{
		EntriesController entriesController = applicationContext.getBean("localEntriesController", EntriesController.class);
		Walk remoteWalk = applicationContext.getBean("remoteWalk", Walk.class);
		Walk localWalk = applicationContext.getBean("localWalk", Walk.class);
		Transmitter transmitter = applicationContext.getBean(Transmitter.class);
		
		entriesController.setOnEntryTransmitted(entry -> {
			transmitter.upload(client, localWalk.toString() + "/" + entry, remoteWalk.toString() + "/" + entry);
		});
	}
	
	private void initializeExplorerService(Client client, Stage stage)
	{
		SimpleExplorerService simpleExplorerService = applicationContext.getBean(SimpleExplorerService.class);
		
		simpleExplorerService.setOnRefresh(() -> {
			refreshRemoteEntriesView(client);
			refreshLocalEntriesView();
		});
		
		simpleExplorerService.setOnClose(() -> {
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
		Walk walk = applicationContext.getBean("remoteWalk", Walk.class);
		Walk localWalk = applicationContext.getBean("localWalk", Walk.class);
		Transmitter transmitter = applicationContext.getBean(Transmitter.class);
		
		entriesController.setOnEntryOpened(entry -> {
			walk.to(entry);
			refreshRemoteEntriesView(client);
		});
		
		entriesController.setOnEntryTransmitted(entry -> {
			transmitter.download(client, walk.toString() + "/" + entry, localWalk.toString() + "/" + entry);
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