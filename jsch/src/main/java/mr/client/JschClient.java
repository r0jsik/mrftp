package mr.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import mr.entry.EntriesProjector;
import mr.entry.JschEntriesProjector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

@RequiredArgsConstructor
public class JschClient implements Client
{
	private final ChannelSftp channel;
	
	@Override
	public void upload(String path, InputStream inputStream)
	{
		try
		{
			channel.put(inputStream, path);
		}
		catch (SftpException exception)
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
		catch (SftpException | IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToDownload(String path, String relativePath, StreamProvider<OutputStream> streamProvider) throws SftpException, IOException
	{
		SftpATTRS attr = channel.lstat(path);
		
		if (attr.isDir())
		{
			tryToDownloadDirectory(path, relativePath, streamProvider);
		}
		else
		{
			tryToDownloadFile(path, relativePath, streamProvider);
		}
	}
	
	private void tryToDownloadDirectory(String path, String relativePath, StreamProvider<OutputStream> streamProvider) throws SftpException, IOException
	{
		String initialDirectory = channel.pwd();
		
		try
		{
			channel.cd(path);
			Vector<ChannelSftp.LsEntry> entries = channel.ls(".");
			
			for (ChannelSftp.LsEntry entry : entries)
			{
				String fileName = entry.getFilename();
				String newPath = String.join("/", path, fileName);
				String newRelativePath = String.join("/", relativePath, fileName);
				
				if ( !fileName.equals(".") && !fileName.equals(".."))
				{
					tryToDownload(newPath, newRelativePath, streamProvider);
				}
			}
		}
		finally
		{
			channel.cd(initialDirectory);
		}
	}
	
	private void tryToDownloadFile(String path, String relativePath, StreamProvider<OutputStream> streamProvider) throws SftpException, IOException
	{
		try (OutputStream outputStream = streamProvider.open(relativePath))
		{
			channel.get(path, outputStream);
		}
	}
	
	@Override
	public void remove(String path)
	{
		try
		{
			tryToRemove(path);
		}
		catch (SftpException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToRemove(String path) throws SftpException
	{
		SftpATTRS attr = channel.lstat(path);
		
		if (attr.isDir())
		{
			channel.rmdir(path);
		}
		else
		{
			channel.rm(path);
		}
	}
	
	@Override
	public void close()
	{
		try
		{
			channel.getSession().disconnect();
		}
		catch (JSchException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public EntriesProjector entriesProjector()
	{
		return new JschEntriesProjector(channel);
	}
}