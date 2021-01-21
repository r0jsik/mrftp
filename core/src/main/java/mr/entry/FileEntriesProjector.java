package mr.entry;

import java.io.File;

public class FileEntriesProjector implements EntriesProjector
{
	@Override
	public void show(String path, EntriesView entriesView) throws EntriesProjectionException
	{
		File parent = new File(path);
		File[] files = parent.listFiles();
		
		if (files == null)
		{
			throw new EntriesProjectionException();
		}
		
		for (File file : files)
		{
			visit(file, entriesView);
		}
	}
	
	private void visit(File file, EntriesView entriesView)
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