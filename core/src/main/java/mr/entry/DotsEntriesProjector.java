package mr.entry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DotsEntriesProjector implements EntriesProjector
{
	private final EntriesProjector entriesProjector;
	
	@Override
	public void show(String path, EntriesView entriesView)
	{
		entriesView.showDirectory(".", 0);
		entriesView.showDirectory("..", 0);
		
		entriesProjector.show(path, entriesView);
	}
}