package mr.client;

import mr.server.MockSshServer;
import mr.stream.MockInputStream;
import mr.stream.MockOutputStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class JschClientTest
{
	private static final ClientFactory clientFactory = new JschClientFactory("./src/test/resources/hosts");
	
	private final Client client;
	
	public JschClientTest() throws ClientFactoryException
	{
		client = clientFactory.create("localhost", 7000, "MrFTP", "MrFTP");
	}
	
	@BeforeAll
	public static void startServer()
	{
		MockSshServer.start();
	}
	
	@AfterAll
	public static void closeServer()
	{
		MockSshServer.close();
	}
	
	@Test
	public void testUpload() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream("Upload test"))
		{
			client.upload("/public/upload.txt", inputStream);
		}
		
		Assertions.assertTrue(() -> (
			MockSshServer.fileExists("/public/upload.txt")
		));
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
	
	@Test
	public void testRemove() throws IOException
	{
		client.remove("/public/remove.txt");
		
		Assertions.assertFalse(() -> (
			MockSshServer.fileExists("/public/remove.txt")
		));
	}
	
	@Test
	public void testUploadAndRemove() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream("Upload and remove test"))
		{
			client.upload("/public/upload-and-remove.txt", inputStream);
		}
		
		client.remove("/public/upload-and-remove.txt");
		
		Assertions.assertFalse(() -> (
			MockSshServer.fileExists("/public/upload-and-remove.txt")
		));
	}
	
	@Test
	public void testRemoveNotExistingFile()
	{
		Assertions.assertThrows(IOException.class, () -> {
			client.remove("/public/not-existing-file");
		});
	}
}