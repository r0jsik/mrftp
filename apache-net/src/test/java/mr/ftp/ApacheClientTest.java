package mr.ftp;

import mr.mock.*;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;

import java.io.IOException;

public class ApacheClientTest
{
	private final static FakeFtpServer fakeFtpServer = new FakeFtpServer();
	
	private final ApacheClient apacheClient;
	
	public ApacheClientTest() throws IOException
	{
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("localhost", 7000);
		ftpClient.login("MrFTP", "MrFTP");
		
		apacheClient = new ApacheClient(ftpClient);
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
			apacheClient.upload("/MrFTP/mock-uploaded-file.txt", inputStream);
			
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
			apacheClient.download("/MrFTP/mock-file.txt", outputStream);
			
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
			apacheClient.download("/MrFTP/not-existing-file.txt", outputStream);
		}
	}
}