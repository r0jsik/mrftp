package mr.init;

import lombok.RequiredArgsConstructor;
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
		remoteEntriesView.hideAll();
		
		String path = remoteWalk.toString();
		EntriesProjector entriesProjector = remoteEntriesViewRefreshEvent.getEntriesProjector();
		entriesProjector.show(path, remoteEntriesView);
		
		remoteEntriesView.onShown();
	}
}