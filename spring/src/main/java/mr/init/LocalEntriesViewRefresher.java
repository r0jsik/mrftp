package mr.init;

import lombok.RequiredArgsConstructor;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.event.LocalEntriesViewRefreshEvent;
import mr.walk.Walk;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalEntriesViewRefresher implements ApplicationListener<LocalEntriesViewRefreshEvent>
{
	private final Walk localWalk;
	private final EntriesView localEntriesView;
	private final EntriesProjector localEntriesProjector;
	
	@Override
	public void onApplicationEvent(LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent)
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