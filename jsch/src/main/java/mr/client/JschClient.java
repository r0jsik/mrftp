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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class JschClient implements Client
{
	private final ChannelSftp channel;
	
	@Override
	public void write(String path, StreamProvider<OutputStream> callback)
	{
		try (OutputStream outputStream = channel.put(path))
		{
			callback.provide(outputStream);
		}
		catch (SftpException | IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void read(String path, StreamProvider<InputStream> callback)
	{
		try (InputStream inputStream = channel.get(path))
		{
			callback.provide(inputStream);
		}
		catch (SftpException | IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void remove(String path)
	{
		try
		{
			SftpATTRS attributes = channel.lstat(path);
			
			if (attributes.isDir())
			{
				channel.rmdir(path);
			}
			else
			{
				channel.rm(path);
			}
		}
		catch (SftpException exception)
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
				
				walk.to(fileName);
				tryToWalk(from, walk, callback);
				walk.out();
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