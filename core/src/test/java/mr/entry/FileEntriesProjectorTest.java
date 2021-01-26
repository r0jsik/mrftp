package mr.entry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileEntriesProjectorTest
{
	@Test
	public void testShow() throws EntriesProjectionException, IOException
	{
		File file = File.createTempFile(".existing-file", "");
		String path = file.getParent();
		String name = file.getName();
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = new FileEntriesProjector();
		entriesProjector.show(path, listEntriesView);
		
		boolean shown = listEntriesView.isShown(name);
		Assertions.assertTrue(shown);
	}
	
	@Test
	public void testNotShow() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = new FileEntriesProjector();
		entriesProjector.show(".", listEntriesView);
		
		boolean shown = listEntriesView.isShown("!@#$%^&*/");
		Assertions.assertFalse(shown);
	}
	
	@Test
	public void testShowInvalidDirectory()
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		EntriesProjector entriesProjector = new FileEntriesProjector();
		
		Assertions.assertThrows(EntriesProjectionException.class, () -> {
			entriesProjector.show("!@#$%^&*", listEntriesView);
		});
	}
}