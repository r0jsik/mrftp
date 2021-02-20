package mr.init;

import javafx.scene.Scene;
import mr.client.ApacheClientFactory;
import mr.client.ClientFactory;
import mr.client.ClientFactoryProvider;
import mr.client.JschClientFactory;
import mr.client.StaticClientFactoryProvider;
import mr.launcher.LauncherController;
import mr.launcher.StageLauncherController;
import mr.scene.SceneFactory;
import mr.settings.Ini4jSettings;
import mr.settings.Settings;
import org.ini4j.Ini;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class LauncherConfiguration
{
	@Bean
	public Scene launcherScene(SceneFactory sceneFactory, LauncherController launcherController)
	{
		return sceneFactory.create("launcher", launcherController);
	}
	
	@Bean
	public LauncherController launcherController()
	{
		return new StageLauncherController();
	}
	
	@Bean
	public ClientFactoryProvider clientFactoryProvider(ClientFactory sshClientFactory, ClientFactory ftpClientFactory, ClientFactory insecureFtpClientFactory)
	{
		return new StaticClientFactoryProvider(sshClientFactory, ftpClientFactory, insecureFtpClientFactory);
	}
	
	@Bean
	public ClientFactory sshClientFactory()
	{
		String userHome = System.getProperty("user.home");
		String sshHosts = String.join("/", userHome, ".ssh", "known_hosts");
		
		return new JschClientFactory(sshHosts);
	}
	
	@Bean
	public ClientFactory ftpClientFactory()
	{
		return new ApacheClientFactory(true);
	}
	
	@Bean
	public ClientFactory insecureFtpClientFactory()
	{
		return new ApacheClientFactory(false);
	}
	
	@Bean
	public Settings settings() throws IOException
	{
		File file = new File("settings.ini");
		Ini ini = new Ini(file);
		
		return new Ini4jSettings(ini);
	}
}