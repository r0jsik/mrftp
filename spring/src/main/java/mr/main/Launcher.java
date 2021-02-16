package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;
import mr.entry.EntriesController;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.explorer.ExplorerController;
import mr.launcher.LauncherController;
import mr.settings.Settings;
import mr.stage.StageInitializer;
import mr.walk.Walk;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
		initializeLauncherController(stage);
		showLauncher(stage);
	}
	
	private void initializeLauncherController(Stage stage)
	{
		LauncherController launcherController = applicationContext.getBean(LauncherController.class);
		
		initializeLauncherForm(launcherController);
		initializeLauncherLabels(launcherController);
		
		launcherController.setOnLaunched((protocol, hostname, port, username, password, remember) -> {
			if (remember)
			{
				rememberSettings(protocol, hostname, port, username, password);
			}
			
			startExplorer(protocol, hostname, port, username, password);
			stage.close();
		});
	}
	
	private void initializeLauncherForm(LauncherController launcherController)
	{
		Settings settings = applicationContext.getBean(Settings.class);
		
		launcherController.setAvailableProtocols("SFTP", "FTP");
		launcherController.setProtocol("SFTP");
		launcherController.setHostname(settings.getHostname());
		launcherController.setPort(settings.getPort());
		launcherController.setUsername(settings.getUsername());
		launcherController.setPassword(settings.getPassword());
	}
	
	private void initializeLauncherLabels(LauncherController launcherController)
	{
		launcherController.setProtocolLabel("Protokół");
		launcherController.setHostnameLabel("Adres serwera");
		launcherController.setPortLabel("Port");
		launcherController.setUsernameLabel("Nazwa użytkownika");
		launcherController.setPasswordLabel("Hasło");
		launcherController.setSettingsLabel("Zapamiętaj dane logowania");
		launcherController.setStartLabel("Połącz");
	}
	
	private void rememberSettings(String protocol, String hostname, int port, String username, String password)
	{
		Settings settings = applicationContext.getBean(Settings.class);
		
		settings.setHostname(hostname);
		settings.setPort(port);
		settings.setUsername(username);
		settings.setPassword(password);
		settings.commit();
	}
	
	private void startExplorer(String protocol, String hostname, int port, String username, String password)
	{
		try
		{
			ClientFactory clientFactory = getClientFactory(protocol);
			Client client = clientFactory.create(hostname, port, username, password);
			
			startExplorer(client, new Stage());
		}
		catch (ClientFactoryException exception)
		{
			exception.printStackTrace();
		}
	}
	
	private ClientFactory getClientFactory(String protocol)
	{
		switch (protocol)
		{
			case "SFTP":
				return applicationContext.getBean("sshClientFactory", ClientFactory.class);
			case "FTP":
				return applicationContext.getBean("ftpClientFactory", ClientFactory.class);
			default:
				throw new IllegalArgumentException();
		}
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
		initializeExplorerController(client, stage);
		initializeRemoteEntriesController(client);
		initializeLocalEntriesController(client);
		refreshRemoteEntriesView(client);
		refreshLocalEntriesView();
		showExplorer(stage);
	}
	
	private void initializeExplorerController(Client client, Stage stage)
	{
		ExplorerController explorerController = applicationContext.getBean(ExplorerController.class);
		explorerController.setCloseLabel("Zamknij");
		explorerController.setRefreshLabel("Odśwież");
		
		explorerController.setOnRefresh(() -> {
			refreshRemoteEntriesView(client);
			refreshLocalEntriesView();
		});
		
		explorerController.setOnClose(() -> {
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
				download(client, remotePath, localPath);
			});
		});
		
		entriesController.setOnEntryDeleted(entry -> {
			usingPathsWithAppendedEntry(entry, (remotePath, localPath) -> {
				remove(client, remotePath);
			});
		});
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
		
		refreshRemoteEntriesView(client);
	}
	
	private void usingPathsWithAppendedEntry(String entry, BiConsumer<String, String> callback)
	{
		Walk remoteWalk = applicationContext.getBean("remoteWalk", Walk.class);
		String remotePath = remoteWalk.toString() + "/" + entry;
		
		Walk localWalk = applicationContext.getBean("localWalk", Walk.class);
		String localPath = localWalk.toString() + "/" + entry;
		
		callback.accept(remotePath, localPath);
	}
	
	private void download(Client client, String remotePath, String localPath)
	{
		try
		{
			tryToDownload(client, remotePath, localPath);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		
		refreshLocalEntriesView();
	}
	
	private void tryToDownload(Client client, String remotePath, String localPath) throws IOException
	{
		File file = new File(localPath);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		
		client.download(remotePath, fileOutputStream);
	}
	
	private void initializeLocalEntriesController(Client client)
	{
		EntriesController entriesController = applicationContext.getBean("localEntriesController", EntriesController.class);
		
		entriesController.setOnEntryOpened(entry -> {
		
		});
		
		entriesController.setOnEntryTransmitted(entry -> {
			usingPathsWithAppendedEntry(entry, (remotePath, localPath) -> {
				upload(client, remotePath, localPath);
			});
		});
		
		entriesController.setOnEntryDeleted(entry -> {
			usingPathsWithAppendedEntry(entry, (remotePath, localPath) -> {
				remove(localPath);
			});
		});
	}
	
	private void remove(String localPath)
	{
		File file = new File(localPath);
		file.delete();
		
		refreshLocalEntriesView();
	}
	
	private void upload(Client client, String remotePath, String localPath)
	{
		try
		{
			tryToUpload(client, remotePath, localPath);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		
		refreshRemoteEntriesView(client);
	}
	
	private void tryToUpload(Client client, String remotePath, String localPath) throws IOException
	{
		File file = new File(localPath);
		FileInputStream fileInputStream = new FileInputStream(file);
		
		client.upload(remotePath, fileInputStream);
	}
	
	private void showExplorer(Stage stage)
	{
		Scene scene = applicationContext.getBean("explorerScene", Scene.class);
		
		StageInitializer stageInitializer = applicationContext.getBean(StageInitializer.class);
		stageInitializer.initializeExplorer(stage, scene);
		
		stage.show();
	}
}