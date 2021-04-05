package mr.entry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileEntriesProjectorTest
{
	@Test
	public void testIsExistingFileShown() throws IOException
	{
		File file = File.createTempFile(".existing-file", "");
		String path = file.getParent();
		String name = file.getName();
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = new FileEntriesProjector();
		entriesProjector.show(path, listEntriesView);
		
		Assertions.assertTrue(() -> (
			listEntriesView.isShown(name)
		));
	}
	
	@Test
	public void testIsNotExistingFileNotShown()
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = new FileEntriesProjector();
		entriesProjector.show(".", listEntriesView);
		
		Assertions.assertFalse(() -> (
			listEntriesView.isShown("!@#$%^&*/")
		));
	}
	
	@Test
	public void testIsInvalidDirectoryNotShown()
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		EntriesProjector entriesProjector = new FileEntriesProjector();
		
		Assertions.assertThrows(EntriesProjectionException.class, () -> {
			entriesProjector.show("!@#$%^&*", listEntriesView);
		});
	}
}