package mr.ftp.entry;

import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class FileEntry implements Entry
{
	private final File file;
	
	@Override
	public String getName()
	{
		return file.getName();
	}
	
	@Override
	public long getSize()
	{
		return file.length();
	}
	
	@Override
	public boolean isDirectory()
	{
		return file.isDirectory();
	}
}