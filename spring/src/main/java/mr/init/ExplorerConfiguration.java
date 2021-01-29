package mr.init;

import javafx.scene.Scene;
import mr.entry.EntriesController;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.entry.FileEntriesProjector;
import mr.explorer.*;
import mr.scene.SceneFactory;
import mr.scene.SceneFactoryException;
import mr.transmitter.FileTransmitter;
import mr.transmitter.Transmitter;
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
	public ExplorerController explorerController(IconLoader iconLoader, ExplorerService explorerService)
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
		return new SimpleExplorerService();
	}
	
	@Bean
	public EntriesProjector localEntriesProjector()
	{
		return new FileEntriesProjector();
	}
	
	@Bean
	public EntriesView remoteEntriesView(ExplorerController explorerController)
	{
		return explorerController.remoteEntriesView();
	}
	
	@Bean
	public EntriesView localEntriesView(ExplorerController explorerController)
	{
		return explorerController.localEntriesView();
	}
	
	@Bean
	public EntriesController remoteEntriesController(ExplorerController explorerController)
	{
		return explorerController.remoteEntriesController();
	}
	
	@Bean
	public EntriesController localEntriesController(ExplorerController explorerController)
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
	
	@Bean
	public Transmitter transmitter()
	{
		return new FileTransmitter();
	}
}