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
			tryToRemove(path);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToRemove(String path) throws IOException
	{
		if ( !ftpClient.deleteFile(path) && !ftpClient.removeDirectory(path))
		{
			throw new ClientActionException();
		}
	}
	
	@Override
	public void walk(String path, Consumer<String> callback)
	{
		int delimiterIndex = path.lastIndexOf('/');
		String home = path.substring(delimiterIndex + 1);
		String from = path.substring(0, delimiterIndex);
		
		Walk walk = new DequeWalk();
		walk.to(home);
		
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
		FTPFile[] ftpFiles = ftpClient.listFiles(absolutePath + "/*");
		
		if (ftpFiles.length == 0)
		{
			callback.accept(walk.toString());
		}
		else
		{
			for (FTPFile ftpFile : ftpFiles)
			{
				String fileName = ftpFile.getName();
				String relativePath = walk.resolve(fileName);
				
				if (ftpFile.isDirectory())
				{
					try
					{
						walk.to(fileName);
						tryToWalk(from, walk, callback);
					}
					finally
					{
						walk.out();
					}
				}
				else
				{
					callback.accept(relativePath);
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