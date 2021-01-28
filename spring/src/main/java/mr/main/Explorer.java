package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.client.Client;
import mr.entry.EntriesController;
import mr.explorer.SimpleExplorerService;
import mr.stage.StageInitializer;
import mr.transmitter.Transmitter;
import mr.walk.Walk;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.BiConsumer;

public class Explorer extends Application
{
	private final ApplicationContext applicationContext;
	
	public static void main(String[] args)
	{
		Application.launch(Explorer.class, args);
	}
	
	public Explorer()
	{
		applicationContext = new AnnotationConfigApplicationContext("mr.init");
	}
	
	@Override
	public void start(Stage stage)
	{
		initializeExplorerService(stage);
		startExplorer(stage);
	}
	
	private void initializeTransmitter(Client client)
	{
		Transmitter transmitter = applicationContext.getBean(Transmitter.class);
		
		initializeUploader(transmitter, client);
		initializeDownloader(transmitter, client);
	}
	
	private void initializeUploader(Transmitter transmitter, Client client)
	{
		EntriesController entriesController = applicationContext.getBean("localEntriesController", EntriesController.class);
		
		entriesController.setOnEntryTransmitted(entry -> {
			usingPaths(entry, (localPath, remotePath) -> {
				transmitter.upload(client, localPath, remotePath);
			});
		});
	}
	
	private void initializeDownloader(Transmitter transmitter, Client client)
	{
		EntriesController entriesController = applicationContext.getBean("remoteEntriesController", EntriesController.class);
		
		entriesController.setOnEntryTransmitted(entry -> {
			usingPaths(entry, (localPath, remotePath) -> {
				transmitter.download(client, localPath, remotePath);
			});
		});
	}
	
	private void initializeExplorerService(Stage stage)
	{
		SimpleExplorerService simpleExplorerService = applicationContext.getBean(SimpleExplorerService.class);
		
		simpleExplorerService.setOnClose(() -> {
			startLauncher();
			stage.close();
		});
		
		simpleExplorerService.setOnRefresh(() -> {
		
		});
	}
	
	private void usingPaths(String entry, BiConsumer<String, String> paths)
	{
		Walk localWalk = applicationContext.getBean("localWalk", Walk.class);
		String localPath = String.join("/", localWalk.toString(), entry);
		
		Walk remoteWalk = applicationContext.getBean("remoteWalk", Walk.class);
		String remotePath = String.join("/", remoteWalk.toString(), entry);
		
		paths.accept(localPath, remotePath);
	}
	
	private void startExplorer(Stage stage)
	{
		Scene scene = applicationContext.getBean("explorerScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeExplorer(stage, scene);
		
		stage.show();
	}
	
	private void startLauncher()
	{
		Stage stage = new Stage();
		Scene scene = applicationContext.getBean("launcherScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeLauncher(stage, scene);
		
		stage.show();
	}
}