package mr.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import mr.entry.EntriesProjector;
import mr.entry.JschEntriesProjector;
import mr.walk.DequeWalk;
import mr.walk.Walk;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.function.Consumer;

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
		catch (SftpException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToWalk(String from, Walk walk, Consumer<String> callback) throws SftpException
	{
		String absolutePath = walk.relate(from);
		SftpATTRS attributes = channel.lstat(absolutePath);
		
		if (attributes.isDir())
		{
			Vector<ChannelSftp.LsEntry> lsEntries = channel.ls(absolutePath + "/*");
			
			for (ChannelSftp.LsEntry lsEntry : lsEntries)
			{
				String fileName = lsEntry.getFilename();
				
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
		}
		else
		{
			callback.accept(walk.toString());
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