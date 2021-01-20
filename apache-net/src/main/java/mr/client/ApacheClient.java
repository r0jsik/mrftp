package mr.client;

import lombok.RequiredArgsConstructor;
import mr.entry.EntriesView;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

@RequiredArgsConstructor
public class ApacheClient implements Client
{
	private final FTPClient ftpClient;
	
	@Override
	public void upload(String path, InputStream inputStream) throws IOException
	{
		boolean done = ftpClient.storeFile(path, inputStream);
		
		if ( !done)
		{
			throw new IOException();
		}
	}
	
	@Override
	public void download(String path, OutputStream outputStream) throws IOException
	{
		boolean done = ftpClient.retrieveFile(path, outputStream);
		
		if ( !done)
		{
			throw new IOException();
		}
	}
	
	@Override
	public void show(String path, EntriesView entriesView) throws IOException
	{
		Arrays.stream(ftpClient.listFiles(path)).forEach(ftpFile -> show(ftpFile, entriesView));
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