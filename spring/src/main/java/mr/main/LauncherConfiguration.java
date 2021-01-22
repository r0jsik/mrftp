package mr.main;

import javafx.scene.Scene;
import mr.client.ApacheClientFactory;
import mr.client.ClientFactory;
import mr.launcher.LauncherController;
import mr.launcher.LauncherService;
import mr.launcher.SimpleLauncherService;
import mr.launcher.StageLauncherController;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LauncherConfiguration
{
	@Bean
	public Scene launcherScene(SceneFactory sceneFactory, LauncherController launcherController) throws SceneFactoryException
	{
		return sceneFactory.create("launcher", launcherController);
	}
	
	@Bean
	public LauncherController launcherController(LauncherService launcherService)
	{
		return new StageLauncherController(launcherService);
	}
	
	@Bean
	public LauncherService launcherService(ClientFactory clientFactory)
	{
		return new SimpleLauncherService(clientFactory);
	}
	
	@Bean
	public ClientFactory clientFactory()
	{
		return new ApacheClientFactory();
	}
}