package mr.main;

import lombok.RequiredArgsConstructor;
import mr.entry.EntriesController;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.walk.Walk;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalEntriesContext
{
	private final Walk localWalk;
	private final EntriesProjector localEntriesProjector;
	private final EntriesView localEntriesView;
	private final EntriesController localEntriesController;
	
	public void initialize()
	{
		refresh();
		
		localEntriesController.setOnEnter(entry -> {
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