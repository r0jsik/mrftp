package mr.ftp;

import lombok.RequiredArgsConstructor;
import mr.ftp.entry.ApacheEntry;
import mr.ftp.entry.Entry;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.function.Consumer;

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
	public void forEach(String path, Consumer<Entry> callback) throws IOException
	{
		Arrays.stream(ftpClient.listFiles(path)).map(ApacheEntry::new).forEach(callback);
	}
}