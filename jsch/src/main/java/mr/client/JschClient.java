package mr.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import mr.entry.EntriesProjector;
import mr.entry.JschEntriesProjector;

import java.io.InputStream;
import java.io.OutputStream;

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
	public void download(String path, OutputStream outputStream)
	{
		try
		{
			channel.get(path, outputStream);
		}
		catch (SftpException exception)
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