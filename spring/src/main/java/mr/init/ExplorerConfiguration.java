package mr.init;

import javafx.scene.Scene;
import mr.entry.EntriesController;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.entry.FileEntriesProjector;
import mr.explorer.*;
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
	public StageExplorerController explorerController(IconLoader iconLoader, ExplorerService explorerService)
	{
		return new StageExplorerController(iconLoader, explorerService);
	}
	
	@Bean
	public IconLoader iconLoader()
	{
		return new ResourcesIconLoader();
	}
	
	@Bean
	public ExplorerService explorerService()
	{
		return new CallbackExplorerService();
	}
	
	@Bean
	public EntriesProjector localEntriesProjector()
	{
		return new FileEntriesProjector();
	}
	
	@Bean
	public EntriesView remoteEntriesView(StageExplorerController explorerController)
	{
		return explorerController.remoteEntriesView();
	}
	
	@Bean
	public EntriesView localEntriesView(StageExplorerController explorerController)
	{
		return explorerController.localEntriesView();
	}
	
	@Bean
	public EntriesController remoteEntriesController(StageExplorerController explorerController)
	{
		return explorerController.remoteEntriesController();
	}
	
	@Bean
	public EntriesController localEntriesController(StageExplorerController explorerController)
	{
		return explorerController.localEntriesController();
	}
	
	@Bean
	public Walk remoteWalk()
	{
		return new DequeWalk();
	}
	
	@Bean
	public Walk localWalk()
	{
		return new DequeWalk();
	}
}