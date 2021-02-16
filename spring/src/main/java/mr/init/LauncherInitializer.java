package mr.init;

import lombok.RequiredArgsConstructor;
import mr.launcher.LauncherController;
import mr.settings.Settings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("launcherScene")
@RequiredArgsConstructor
public class LauncherInitializer implements InitializingBean
{
	private final LauncherController launcherController;
	private final Settings settings;
	
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
}