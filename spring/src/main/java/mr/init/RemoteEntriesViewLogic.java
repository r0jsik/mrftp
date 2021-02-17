package mr.init;

import lombok.RequiredArgsConstructor;
import mr.entry.EntriesProjectionException;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.event.RemoteEntriesViewRefreshEvent;
import mr.walk.Walk;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoteEntriesViewLogic implements ApplicationListener<RemoteEntriesViewRefreshEvent>
{
	private final EntriesView remoteEntriesView;
	private final Walk remoteWalk;
	
	@Override
	public void onApplicationEvent(RemoteEntriesViewRefreshEvent remoteEntriesViewRefreshEvent)
	{
		String path = remoteWalk.toString();
		
		try
		{
			remoteEntriesView.hideAll();
			
			EntriesProjector entriesProjector = remoteEntriesViewRefreshEvent.getEntriesProjector();
			entriesProjector.show(path, remoteEntriesView);
		}
		catch (EntriesProjectionException exception)
		{
			exception.printStackTrace();
		}
	}
}