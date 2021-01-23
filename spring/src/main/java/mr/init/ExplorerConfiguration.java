package mr.init;

import javafx.scene.Scene;
import mr.entry.EntriesController;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.entry.FileEntriesProjector;
import mr.explorer.ExplorerController;
import mr.explorer.IconLoader;
import mr.explorer.ResourcesIconLoader;
import mr.explorer.StageExplorerController;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
import mr.walk.DequeWalk;
import mr.walk.Walk;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExplorerConfiguration
{
	@Bean
	public Scene explorerScene(SceneFactory sceneFactory, ExplorerController explorerController) throws SceneFactoryException
	{
		return sceneFactory.create("explorer", explorerController);
	}
	
	@Bean
	public ExplorerController explorerController(IconLoader iconLoader)
	{
		return new StageExplorerController(iconLoader);
	}
	
	@Bean
	public IconLoader iconLoader()
	{
		return new ResourcesIconLoader();
	}
	
	@Bean
	public EntriesProjector localEntriesProjector()
	{
		return new FileEntriesProjector();
	}
	
	@Bean
	public EntriesView localEntriesView(ExplorerController explorerController)
	{
		return explorerController.localEntriesView();
	}
	
	@Bean
	public EntriesController localEntriesController(ExplorerController explorerController)
	{
		return explorerController.localEntriesController();
	}
	
	@Bean
	public Walk localWalk()
	{
		return new DequeWalk();
	}
}