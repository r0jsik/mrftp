package mr.entry;

import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;

import java.util.Vector;

@RequiredArgsConstructor
public class JschEntriesProjector implements EntriesProjector
{
	private final Session session;
	
	@Override
	public void show(String path, EntriesView entriesView) throws EntriesProjectionException
	{
		try
		{
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			
			Vector<ChannelSftp.LsEntry> vector = channel.ls(path);
			vector.forEach(entry -> show(entry, entriesView));
			
			channel.exit();
		}
		catch (JSchException | SftpException exception)
		{
			throw new EntriesProjectionException();
		}
	}
	
	private void show(ChannelSftp.LsEntry entry, EntriesView entriesView)
	{
		SftpATTRS attrs = entry.getAttrs();
		
		if (attrs.isDir())
		{
			entriesView.showDirectory(entry.getFilename(), attrs.getSize());
		}
		else
		{
			entriesView.showFile(entry.getFilename(), attrs.getSize());
		}
	}
}