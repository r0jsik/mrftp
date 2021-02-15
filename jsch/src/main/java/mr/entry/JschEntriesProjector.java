package mr.entry;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;

import java.util.Vector;

@RequiredArgsConstructor
public class JschEntriesProjector implements EntriesProjector
{
	private final ChannelSftp channel;
	
	@Override
	public void show(String path, EntriesView entriesView) throws EntriesProjectionException
	{
		try
		{
			Vector<ChannelSftp.LsEntry> vector = channel.ls(path);
			vector.forEach(entry -> show(entry, entriesView));
		}
		catch (SftpException exception)
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