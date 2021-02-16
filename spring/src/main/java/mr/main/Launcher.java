package mr.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;
import mr.event.ClientChangedEvent;
import mr.explorer.ExplorerController;
import mr.launcher.LauncherController;
import mr.settings.Settings;
import mr.stage.StageInitializer;
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
		initializeLauncherController(stage);
		showLauncher(stage);
	}
	
	private void initializeLauncherController(Stage stage)
	{
		LauncherController launcherController = applicationContext.getBean(LauncherController.class);
		
		launcherController.setOnLaunched((protocol, hostname, port, username, password, remember) -> {
			if (remember)
			{
				rememberSettings(protocol, hostname, port, username, password);
			}
			
			startExplorer(protocol, hostname, port, username, password);
			stage.close();
		});
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
		applicationContext.publishEvent(new ClientChangedEvent(this, client));
		
		initializeExplorerController(stage);
		showExplorer(stage);
	}
	
	private void initializeExplorerController(Stage stage)
	{
		ExplorerController explorerController = applicationContext.getBean(ExplorerController.class);
		
		explorerController.setOnClose(() -> {
			// client.close()
			startLauncher(new Stage());
			stage.close();
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