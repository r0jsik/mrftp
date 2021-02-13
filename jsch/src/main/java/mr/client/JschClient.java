package mr.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import mr.entry.EntriesProjector;
import mr.entry.JschEntriesProjector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RequiredArgsConstructor
public class JschClient implements Client
{
	private final ChannelSftp channel;
	
	@Override
	public void upload(String path, InputStream inputStream) throws IOException
	{
		try
		{
			channel.put(inputStream, path);
		}
		catch (SftpException exception)
		{
			throw new IOException(exception);
		}
	}
	
	@Override
	public void download(String path, OutputStream outputStream) throws IOException
	{
		try
		{
			channel.get(path, outputStream);
		}
		catch (SftpException exception)
		{
			throw new IOException(exception);
		}
	}
	
	@Override
	public EntriesProjector entriesProjector()
	{
		return new JschEntriesProjector(channel);
	}
}