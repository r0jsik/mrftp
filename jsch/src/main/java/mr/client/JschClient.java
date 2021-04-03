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
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class JschClient implements Client
{
	private final ChannelSftp channel;
	
	@Override
	public void write(String path, StreamProvider<OutputStream> callback)
	{
		createParentDirectories(path);
		
		try (OutputStream outputStream = channel.put(path))
		{
			callback.provide(outputStream);
		}
		catch (SftpException | IOException exception)
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
				channel.mkdir(parentBuilder.toString());
			}
			catch (SftpException exception)
			{
				// Directory already exists - ignore the exception
			}
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
		walk("", path, this::remove);
	}
	
	private void remove(String relativePath, boolean isDirectory)
	{
		try
		{
			tryToRemove(relativePath, isDirectory);
		}
		catch (SftpException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToRemove(String relativePath, boolean isDirectory) throws SftpException
	{
		if (isDirectory)
		{
			channel.rmdir(relativePath);
		}
		else
		{
			channel.rm(relativePath);
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
		catch (SftpException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToWalk(String from, Walk walk, BiConsumer<String, Boolean> callback) throws SftpException
	{
		String absolutePath = from + walk.toString();
		SftpATTRS attributes = channel.lstat(absolutePath);
		String relativePath = walk.toString();
		boolean isDirectory = attributes.isDir();
		
		if (isDirectory)
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
		
		callback.accept(relativePath, isDirectory);
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