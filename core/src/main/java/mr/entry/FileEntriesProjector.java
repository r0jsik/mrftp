package mr.entry;

import java.io.File;

public class FileEntriesProjector implements EntriesProjector
{
	@Override
	public void show(String path, EntriesView entriesView)
	{
		File parent = new File(path);
		File[] files = parent.listFiles();
		
		if (files == null)
		{
			throw new EntriesProjectionException();
		}
		
		for (File file : files)
		{
			show(file, entriesView);
		}
	}
	
	private void show(File file, EntriesView entriesView)
	{
		String name = file.getName();
		long size = file.length();
		
		if (file.isDirectory())
		{
			entriesView.showDirectory(name, size);
		}
		else
		{
			entriesView.showFile(name, size);
		}
	}
}