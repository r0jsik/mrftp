package mr.main;

import javafx.application.Platform;
import mr.explorer.ExplorerController;
import mr.launcher.LauncherController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextBuildTest
{
	private final ApplicationContext applicationContext;
	
	@BeforeAll
	public static void startPlatform()
	{
		Platform.startup(() -> {});
	}
	
	public ContextBuildTest()
	{
		applicationContext = new AnnotationConfigApplicationContext("mr.init");
	}
	
	@Test
	public void testLauncherBuilds()
	{
		Assertions.assertDoesNotThrow(() -> {
			applicationContext.getBean(LauncherController.class);
			applicationContext.getBean("launcherScene");
		});
	}
	
	@Test
	public void testExplorerBuilds()
	{
		Assertions.assertDoesNotThrow(() -> {
			applicationContext.getBean(ExplorerController.class);
			applicationContext.getBean("explorerScene");
		});
	}
}