package mr.init;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;
import mr.event.StartExplorerEvent;
import mr.event.StartLauncherEvent;
import mr.launcher.LauncherController;
import mr.settings.Settings;
import mr.stage.StageInitializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LauncherLogic implements InitializingBean, ApplicationListener<StartLauncherEvent>
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final LauncherController launcherController;
	private final Settings settings;
	private final ClientFactory sshClientFactory;
	private final ClientFactory ftpClientFactory;
	private final Scene launcherScene;
	private final StageInitializer stageInitializer;
	
	@Override
	public void afterPropertiesSet()
	{
		initializeLabels();
		initializeForm();
	}
	
	private void initializeLabels()
	{
		launcherController.setProtocolLabel("Protokół");
		launcherController.setHostnameLabel("Adres serwera");
		launcherController.setPortLabel("Port");
		launcherController.setUsernameLabel("Nazwa użytkownika");
		launcherController.setPasswordLabel("Hasło");
		launcherController.setSettingsLabel("Zapamiętaj dane logowania");
		launcherController.setStartLabel("Połącz");
	}
	
	private void initializeForm()
	{
		launcherController.setAvailableProtocols("SFTP", "FTP");
		launcherController.setProtocol("SFTP");
		launcherController.setHostname(settings.getHostname());
		launcherController.setPort(settings.getPort());
		launcherController.setUsername(settings.getUsername());
		launcherController.setPassword(settings.getPassword());
	}
	
	@Override
	public void onApplicationEvent(StartLauncherEvent startLauncherEvent)
	{
		Stage stage = startLauncherEvent.getStage();
		
		initializeLauncherController(stage);
		showLauncher(stage);
	}
	
	private void initializeLauncherController(Stage stage)
	{
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
			
			StartExplorerEvent startExplorerEvent = new StartExplorerEvent(this, client, new Stage());
			applicationEventPublisher.publishEvent(startExplorerEvent);
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
				return sshClientFactory;
			case "FTP":
				return ftpClientFactory;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	private void showLauncher(Stage stage)
	{
		stageInitializer.initializeLauncher(stage, launcherScene);
		stage.show();
	}
}