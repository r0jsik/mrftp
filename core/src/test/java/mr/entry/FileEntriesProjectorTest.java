package mr.entry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileEntriesProjectorTest
{
	private final EntriesProjector entriesProjector;
	
	public FileEntriesProjectorTest()
	{
		this.entriesProjector = new FileEntriesProjector();
	}
	
	@Test
	public void testShow() throws EntriesProjectionException, IOException
	{
		File file = File.createTempFile(".existing-file", "");
		String path = file.getParent();
		String name = file.getName();
		ListEntriesView listEntriesView = new ListEntriesView();
		
		entriesProjector.show(path, listEntriesView);
		
		boolean shown = listEntriesView.isShown(name);
		Assertions.assertTrue(shown);
	}
	
	@Test
	public void testNotShow() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		entriesProjector.show(".", listEntriesView);
		
		boolean shown = listEntriesView.isShown("not existing file");
		Assertions.assertFalse(shown);
	}
}