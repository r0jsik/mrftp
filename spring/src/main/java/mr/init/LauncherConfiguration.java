package mr.init;

import javafx.scene.Scene;
import mr.client.ApacheClientFactory;
import mr.client.JschClientFactory;
import mr.launcher.CallbackLauncherService;
import mr.launcher.LauncherController;
import mr.launcher.LauncherService;
import mr.launcher.StageLauncherController;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
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
	public Scene launcherScene(SceneFactory sceneFactory, LauncherController launcherController) throws SceneFactoryException
	{
		return sceneFactory.create("launcher", launcherController);
	}
	
	@Bean
	public LauncherController launcherController(LauncherService launcherService, Settings settings)
	{
		return new StageLauncherController(launcherService, settings);
	}
	
	@Bean
	public LauncherService launcherService()
	{
		return new CallbackLauncherService(new JschClientFactory(), new ApacheClientFactory());
	}
	
	@Bean
	public Settings settings() throws IOException
	{
		File file = new File("settings.ini");
		Ini ini = new Ini(file);
		ini.store();
		Settings settings = new Ini4jSettings(ini, IOException::printStackTrace);
		
		return settings;
	}
}