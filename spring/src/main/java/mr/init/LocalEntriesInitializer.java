package mr.init;

import lombok.RequiredArgsConstructor;
import mr.entry.EntriesController;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.walk.Walk;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("explorerScene")
@RequiredArgsConstructor
public class LocalEntriesInitializer implements InitializingBean
{
	private final Walk localWalk;
	private final EntriesProjector localEntriesProjector;
	private final EntriesView localEntriesView;
	private final EntriesController localEntriesController;
	
	@Override
	public void afterPropertiesSet()
	{
		refresh();
		
		localEntriesController.setOnEntryOpened(entry -> {
			localWalk.to(entry);
			refresh();
		});
	}
	
	private void refresh()
	{
		String path = localWalk.toString();
		
		try
		{
			localEntriesView.hideAll();
			localEntriesProjector.show(path, localEntriesView);
		}
		catch (EntriesProjectionException exception)
		{
			exception.printStackTrace();
		}
	}
}