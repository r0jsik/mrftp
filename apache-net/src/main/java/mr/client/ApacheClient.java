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
	public void download(String path, StreamProvider<OutputStream> streamProvider)
	{
		try
		{
			tryToDownload(path, "", streamProvider);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToDownload(String path, String relativePath, StreamProvider<OutputStream> streamProvider) throws IOException
	{
		String initialDirectory = ftpClient.printWorkingDirectory();
		
		try
		{
			boolean isDirectory = ftpClient.changeWorkingDirectory(path);
			
			if (isDirectory)
			{
				tryToDownloadDirectory(path, relativePath, streamProvider);
			}
			else
			{
				tryToDownloadFile(path, relativePath, streamProvider);
			}
		}
		finally
		{
			ftpClient.changeWorkingDirectory(initialDirectory);
		}
	}
	
	private void tryToDownloadDirectory(String path, String relativePath, StreamProvider<OutputStream> streamProvider) throws IOException
	{
		for (String fileName : ftpClient.listNames(path))
		{
			String newPath = String.join("/", path, fileName);
			String newRelativePath = String.join("/", relativePath, fileName);
			
			tryToDownload(newPath, newRelativePath, streamProvider);
		}
	}
	
	private void tryToDownloadFile(String path, String relativePath, StreamProvider<OutputStream> streamProvider) throws IOException
	{
		try (OutputStream outputStream = streamProvider.open(relativePath))
		{
			if ( !ftpClient.retrieveFile(path, outputStream))
			{
				throw new ClientActionException();
			}
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