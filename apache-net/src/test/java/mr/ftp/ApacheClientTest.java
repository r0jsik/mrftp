package mr.ftp;

import mr.ftp.entry.Entry;
import mr.mock.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ApacheClientTest
{
	private static final FakeFtpServer fakeFtpServer = new FakeFtpServer();
	private static final ClientFactory clientFactory = new InsecureApacheClientFactory();
	
	private final Client client;
	
	public ApacheClientTest() throws ClientFactoryException
	{
		client = clientFactory.create("localhost", 7000, "MrFTP", "MrFTP");
	}
	
	@BeforeAll
	public static void startServer()
	{
		FileSystemDirector fileSystemDirector = new SimpleFileSystemDirector();
		
		ServerBuilder serverBuilder = new FakeServerBuilder(fakeFtpServer);
		serverBuilder.createFileSystem(fileSystemDirector);
		serverBuilder.createUser("MrFTP", "MrFTP");
		
		fakeFtpServer.setServerControlPort(7000);
		fakeFtpServer.start();
	}
	
	@AfterAll
	public static void closeServer()
	{
		fakeFtpServer.stop();
	}
	
	@Test
	public void testUpload() throws IOException
	{
		String sentText = "Mock content";
		
		try (MockInputStream inputStream = new MockInputStream(sentText))
		{
			client.upload("/MrFTP/mock-uploaded-file.txt", inputStream);
			
			boolean uploaded = fileExists("/MrFTP/mock-uploaded-file.txt");
			Assertions.assertTrue(uploaded);
		}
	}
	
	private boolean fileExists(String path)
	{
		return fakeFtpServer.getFileSystem().exists(path);
	}
	
	@Test
	public void testDownload() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			client.download("/MrFTP/mock-file.txt", outputStream);
			
			byte[] expected = "Mock content".getBytes();
			byte[] actual = outputStream.toByteArray();
			
			Assertions.assertArrayEquals(expected, actual);
		}
	}
	
	@Test
	public void testDownloadNotExistingFile()
	{
		Assertions.assertThrows(IOException.class, this::downloadNotExistingFile);
	}
	
	private void downloadNotExistingFile() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			client.download("/MrFTP/not-existing-file.txt", outputStream);
		}
	}
	
	@Test
	public void testForEach() throws IOException
	{
		AtomicBoolean fileExists = new AtomicBoolean(false);
		
		client.forEach("/MrFTP", entry -> {
			if (isEntryToFind(entry))
			{
				fileExists.set(true);
			}
		});
		
		Assertions.assertTrue(fileExists.get());
	}
	
	private boolean isEntryToFind(Entry entry)
	{
		return entry.getName().equals("existing-file");
	}
}