package mr.client;

import lombok.RequiredArgsConstructor;
import mr.entry.ApacheEntriesProjector;
import mr.entry.EntriesProjector;
import mr.walk.DequeWalk;
import mr.walk.Walk;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class ApacheClient implements Client
{
	private final FTPClient ftpClient;
	
	@Override
	public void write(String path, StreamProvider<OutputStream> callback)
	{
		createParentDirectories(path);
		
		try (OutputStream outputStream = ftpClient.storeFileStream(path))
		{
			callback.provide(outputStream);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void createParentDirectories(String path)
	{
		StringBuilder parentBuilder = new StringBuilder();
		String[] nodes = path.split("/");
		
		for (int i = 1; i < nodes.length - 1; i++)
		{
			parentBuilder.append('/').append(nodes[i]);
			
			try
			{
				ftpClient.makeDirectory(parentBuilder.toString());
			}
			catch (IOException exception)
			{
				// Directory already exists - ignore the exception
			}
		}
	}
	
	@Override
	public void read(String path, StreamProvider<InputStream> callback)
	{
		try (InputStream inputStream = ftpClient.retrieveFileStream(path))
		{
			if (inputStream == null)
			{
				throw new ClientActionException();
			}
			
			callback.provide(inputStream);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void remove(String path)
	{
		walk("", path, this::remove);
	}
	
	private void remove(String relativePath, boolean isDirectory)
	{
		try
		{
			if ( !tryToRemove(relativePath, isDirectory))
			{
				throw new ClientActionException();
			}
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private boolean tryToRemove(String relativePath, boolean isDirectory) throws IOException
	{
		if (isDirectory)
		{
			return ftpClient.removeDirectory(relativePath);
		}
		else
		{
			return ftpClient.deleteFile(relativePath);
		}
	}
	
	@Override
	public void walk(String from, String entry, BiConsumer<String, Boolean> callback)
	{
		Walk walk = new DequeWalk();
		walk.to(entry);
		
		try
		{
			tryToWalk(from, walk, callback);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToWalk(String from, Walk walk, BiConsumer<String, Boolean> callback) throws IOException
	{
		String absolutePath = from + walk.toString();
		FTPFile[] filesList = ftpClient.listFiles(absolutePath + "/*");
		
		for (FTPFile ftpFile : filesList)
		{
			String fileName = ftpFile.getName();
			String relativePath = walk.resolve(fileName);
			boolean isDirectory = ftpFile.isDirectory();
			
			if (isDirectory)
			{
				walk.to(fileName);
				tryToWalk(from, walk, callback);
				walk.out();
			}
			else
			{
				callback.accept(relativePath, false);
			}
		}
		
		callback.accept(walk.toString(), filesList.length != 0);
	}
	
	@Override
	public void close()
	{
		try
		{
			ftpClient.logout();
			ftpClient.disconnect();
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public EntriesProjector entriesProjector()
	{
		return new ApacheEntriesProjector(ftpClient);
	}
}