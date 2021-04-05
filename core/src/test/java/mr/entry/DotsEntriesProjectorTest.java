package mr.entry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DotsEntriesProjectorTest
{
	@Test
	public void testIsSingleDotShown()
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = new DotsEntriesProjector((path, entriesView) -> {});
		entriesProjector.show(".", listEntriesView);
		
		Assertions.assertTrue(() -> (
			listEntriesView.isShown(".")
		));
	}
	
	@Test
	public void testIsDoubleDotShown()
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = new DotsEntriesProjector((path, entriesView) -> {});
		entriesProjector.show(".", listEntriesView);
		
		Assertions.assertTrue(() -> (
			listEntriesView.isShown("..")
		));
	}
}