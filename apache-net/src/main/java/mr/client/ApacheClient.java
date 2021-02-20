package mr.client;

import lombok.RequiredArgsConstructor;
import mr.entry.ApacheEntriesProjector;
import mr.entry.EntriesProjector;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
			if ( !ftpClient.deleteFile(path))
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