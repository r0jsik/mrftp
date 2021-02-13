package mr.client;

import mr.stream.MockInputStream;
import mr.stream.MockOutputStream;
import mr.server.MockFtpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApacheClientTest
{
	private static final ClientFactory clientFactory = new InsecureApacheClientFactory();
	
	private final Client client;
	
	public ApacheClientTest() throws ClientFactoryException
	{
		client = clientFactory.create("localhost", 7000, "MrFTP", "MrFTP");
	}
	
	@BeforeAll
	public static void startServer()
	{
		MockFtpServer.start();
	}
	
	@AfterAll
	public static void closeServer()
	{
		MockFtpServer.close();
	}
	
	@Test
	public void testUpload() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream("Upload test"))
		{
			client.upload("/public/upload.txt", inputStream);
		}
		
		Assertions.assertTrue(() -> (
			MockFtpServer.fileExists("/public/upload.txt")
		));
	}
	
	@Test
	public void testUploadToPrivateDirectory() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream("Upload test"))
		{
			Assertions.assertThrows(IOException.class, () -> {
				client.upload("/private/virus.php", inputStream);
			});
		}
	}
	
	@Test
	public void testDownload() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			client.download("/public/download.txt", outputStream);
			
			Assertions.assertTrue(() -> (
				outputStream.hasContent("Download test")
			));
		}
	}
	
	@Test
	public void testDownloadFromPrivateDirectory() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			Assertions.assertThrows(IOException.class, () -> {
				client.download("/private/auth", outputStream);
			});
		}
	}
	
	@Test
	public void testDownloadNotExistingFile() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			Assertions.assertThrows(IOException.class, () -> {
				client.download("/public/not-existing-file", outputStream);
			});
		}
	}
	
	@Test
	public void testUploadAndThenDownload() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream("Upload and download test"))
		{
			client.upload("/public/upload-and-download.txt", inputStream);
		}
		
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			client.download("/public/upload-and-download.txt", outputStream);
			
			Assertions.assertTrue(() -> (
				outputStream.hasContent("Upload and download test")
			));
		}
	}
}