package mr.entry;

import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class ApacheEntriesProjector implements EntriesProjector
{
	private final FTPClient ftpClient;
	
	@Override
	public void show(String path, EntriesView entriesView)
	{
		try
		{
			Arrays.stream(ftpClient.listFiles(path)).forEach(ftpFile -> show(ftpFile, entriesView));
		}
		catch (IOException exception)
		{
			throw new EntriesProjectionException();
		}
	}
	
	private void show(FTPFile ftpFile, EntriesView entriesView)
	{
		String name = ftpFile.getName();
		long size = ftpFile.getSize();
		
		if (ftpFile.isDirectory())
		{
			entriesView.showDirectory(name, size);
		}
		else
		{
			entriesView.showFile(name, size);
		}
	}
}