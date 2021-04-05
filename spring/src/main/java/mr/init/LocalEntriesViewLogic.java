package mr.init;

import lombok.RequiredArgsConstructor;
import mr.entry.EntriesProjector;
import mr.entry.EntriesView;
import mr.event.LocalEntriesViewRefreshEvent;
import mr.walk.Walk;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalEntriesViewLogic implements ApplicationListener<LocalEntriesViewRefreshEvent>
{
	private final Walk localWalk;
	private final EntriesView localEntriesView;
	private final EntriesProjector localEntriesProjector;
	
	@Override
	public void onApplicationEvent(LocalEntriesViewRefreshEvent localEntriesViewRefreshEvent)
	{
		String path = localWalk.toString();
		
		localEntriesView.hideAll();
		localEntriesProjector.show(path, localEntriesView);
		localEntriesView.onShown();
	}
}