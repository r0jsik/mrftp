package mr.init;

import lombok.RequiredArgsConstructor;
import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryProvider;
import mr.event.StartExplorerEvent;
import mr.launcher.LauncherController;
import mr.settings.Settings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("launcherScene")
@RequiredArgsConstructor
public class LauncherLogic implements InitializingBean
{
	private final ApplicationEventPublisher applicationEventPublisher;
	private final LauncherController launcherController;
	private final Settings settings;
	private final ClientFactoryProvider clientFactoryProvider;
	
	@Override
	public void afterPropertiesSet()
	{
		initializeForm();
		initializeOnRemember();
		initializeOnLaunched();
	}
	
	private void initializeForm()
	{
		launcherController.setAvailableProtocols("SFTP", "FTP", "FTP (bez szyfrowania)");
		
		settings.select(context -> {
			launcherController.setProtocol(context.getProtocol());
			launcherController.setHost(context.getHost());
			launcherController.setPort(context.getPort());
			launcherController.setUsername(context.getUsername());
			launcherController.setPassword(context.getPassword());
		});
	}
	
	private void initializeOnRemember()
	{
		launcherController.setOnRemember((protocol, host, port, username, password) -> {
			settings.update(context -> {
				context.setProtocol(protocol);
				context.setHost(host);
				context.setPort(port);
				context.setUsername(username);
				context.setPassword(password);
			});
		});
	}
	
	private void initializeOnLaunched()
	{
		launcherController.setOnLaunched((protocol, host, port, username, password) -> {
			ClientFactory clientFactory = clientFactoryProvider.getByProtocol(protocol);
			Client client = clientFactory.create(host, port, username, password);
			String status = String.join("", username, "@", host, ":", String.valueOf(port));
			StartExplorerEvent startExplorerEvent = new StartExplorerEvent(this, client, status);
			
			applicationEventPublisher.publishEvent(startExplorerEvent);
		});
	}
}