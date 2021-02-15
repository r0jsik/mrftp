package mr.filesystem.builder;

import java.io.*;
import java.nio.file.Path;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NativeFileSystemBuilder implements FileSystemBuilder
{
	private final Path home;
	
	@Override
	public void createDirectory(String path)
	{
		File file = new File(home + path);
		file.mkdir();
	}
	
	@Override
	public void createInaccessibleDirectory(String path)
	{
		File file = new File(home + path);
		file.setReadable(false);
		file.mkdir();
	}
	
	@Override
	public void createFile(String path)
	{
		File file = new File(home + path);
		
		try
		{
			file.createNewFile();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	@Override
	public void createFile(String path, String content)
	{
		File file = new File(home + path);
		
		try
		{
			OutputStream outputStream = new FileOutputStream(file);
			outputStream.write(content.getBytes());
			outputStream.close();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
}