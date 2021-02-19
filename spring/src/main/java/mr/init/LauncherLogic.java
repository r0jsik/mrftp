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
	private final ClientFactory insecureFtpClientFactory;
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
		launcherController.setAvailableProtocols("SFTP", "FTP", "FTP (bez szyfrowania)");
		
		settings.select(context -> {
			launcherController.setProtocol(context.getProtocol());
			launcherController.setHostname(context.getHostname());
			launcherController.setPort(context.getPort());
			launcherController.setUsername(context.getUsername());
			launcherController.setPassword(context.getPassword());
		});
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
		launcherController.setOnRemember((protocol, hostname, port, username, password) -> {
			settings.update(context -> {
				context.setProtocol(protocol);
				context.setHostname(hostname);
				context.setPort(port);
				context.setUsername(username);
				context.setPassword(password);
			});
		});
		
		launcherController.setOnLaunched((protocol, hostname, port, username, password) -> {
			startExplorer(protocol, hostname, port, username, password);
			stage.close();
		});
	}
	
	private void startExplorer(String protocol, String hostname, int port, String username, String password)
	{
		try
		{
			ClientFactory clientFactory = getClientFactory(protocol);
			Client client = clientFactory.create(hostname, port, username, password);
			String status = String.join("", username, "@", hostname, ":", String.valueOf(port));
			
			StartExplorerEvent startExplorerEvent = new StartExplorerEvent(this, client, new Stage(), status);
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
				return insecureFtpClientFactory;
		}
	}
	
	private void showLauncher(Stage stage)
	{
		stageInitializer.initializeLauncher(stage, launcherScene);
		stage.show();
	}
}