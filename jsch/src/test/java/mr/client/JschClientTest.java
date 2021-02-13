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
	private static final ClientFactory clientFactory = new JschClientFactory();
	
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
			client.upload("./src/test/resources/upload.txt", inputStream);
		}
		
		Assertions.assertTrue(() -> (
			MockSshServer.fileExists("./src/test/resources/upload.txt")
		));
	}
	
	@Test
	public void testDownload() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			client.download("./src/test/resources/download.txt", outputStream);
			
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
				client.download("./src/test/resources/not-existing-file", outputStream);
			});
		}
	}
	
	@Test
	public void testUploadAndThenDownload() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream("Upload and download test"))
		{
			client.upload("./src/test/resources/upload-and-download.txt", inputStream);
		}
		
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			client.download("./src/test/resources/upload-and-download.txt", outputStream);
			
			Assertions.assertTrue(() -> (
				outputStream.hasContent("Upload and download test")
			));
		}
	}
}