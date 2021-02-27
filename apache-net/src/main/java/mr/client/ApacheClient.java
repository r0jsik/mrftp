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
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ApacheClient implements Client
{
	private final FTPClient ftpClient;
	
	@Override
	public void upload(String path, InputStream inputStream)
	{
		try
		{
			if ( !ftpClient.storeFile(path, inputStream))
			{
				throw new ClientActionException();
			}
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void download(String path, OutputStream outputStream)
	{
		try
		{
			if ( !ftpClient.retrieveFile(path, outputStream))
			{
				throw new ClientActionException();
			}
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void remove(String path)
	{
		try
		{
			if ( !ftpClient.deleteFile(path) && !ftpClient.removeDirectory(path))
			{
				throw new ClientActionException();
			}
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void walk(String from, String entry, Consumer<String> callback)
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
	
	private void tryToWalk(String from, Walk walk, Consumer<String> callback) throws IOException
	{
		String absolutePath = walk.relate(from);
		FTPFile[] filesList = ftpClient.listFiles(absolutePath + "/*");
		
		if (filesList.length == 0)
		{
			callback.accept(walk.toString());
		}
		else
		{
			for (FTPFile ftpFile : filesList)
			{
				String fileName = ftpFile.getName();
				
				if (ftpFile.isDirectory())
				{
					walk.to(fileName);
					tryToWalk(from, walk, callback);
					walk.out();
				}
				else
				{
					callback.accept(walk.resolve(fileName));
				}
			}
		}
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