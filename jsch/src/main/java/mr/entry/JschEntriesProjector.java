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
	public void show(String path, EntriesView entriesView)
	{
		try
		{
			Vector<ChannelSftp.LsEntry> lsEntries = channel.ls(path);
			
			for (ChannelSftp.LsEntry lsEntry : lsEntries)
			{
				show(lsEntry, entriesView);
			}
		}
		catch (SftpException exception)
		{
			throw new EntriesProjectionException();
		}
	}
	
	private void show(ChannelSftp.LsEntry entry, EntriesView entriesView)
	{
		String fileName = entry.getFilename();
		SftpATTRS attributes = entry.getAttrs();
		long size = attributes.getSize();
		
		if (attributes.isDir())
		{
			entriesView.showDirectory(fileName, size);
		}
		else
		{
			entriesView.showFile(fileName, size);
		}
	}
}