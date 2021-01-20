package mr.entry;

import mr.mock.MockEntriesView;
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
		MockEntriesView mockEntriesView = new MockEntriesView();
		
		entriesProjector.show(path, mockEntriesView);
		
		boolean shown = mockEntriesView.isShown(name);
		Assertions.assertTrue(shown);
	}
	
	@Test
	public void testNotShow() throws EntriesProjectionException
	{
		MockEntriesView mockEntriesView = new MockEntriesView();
		
		entriesProjector.show(".", mockEntriesView);
		
		boolean shown = mockEntriesView.isShown("not existing file");
		Assertions.assertFalse(shown);
	}
}