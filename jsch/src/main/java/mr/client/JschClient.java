package mr.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
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
	private final Session session;
	
	@Override
	public void upload(String path, InputStream inputStream) throws IOException
	{
		try
		{
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			channel.put(inputStream, path);
			channel.exit();
		}
		catch (JSchException | SftpException exception)
		{
			throw new IOException(exception);
		}
	}
	
	@Override
	public void download(String path, OutputStream outputStream) throws IOException
	{
		try
		{
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			channel.get(path, outputStream);
			channel.exit();
		}
		catch (JSchException | SftpException exception)
		{
			throw new IOException(exception);
		}
	}
	
	@Override
	public EntriesProjector entriesProjector()
	{
		return new JschEntriesProjector(session);
	}
}