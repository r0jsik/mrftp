package mr.client;

import mr.mock.MockInputStream;
import mr.mock.MockOutputStream;
import mr.mock.MockServer;
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
		MockServer.start();
	}
	
	@AfterAll
	public static void closeServer()
	{
		MockServer.close();
	}
	
	@Test
	public void testUpload() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream())
		{
			client.upload("/MrFTP/mock-uploaded-file.txt", inputStream);
			
			boolean uploaded = MockServer.fileExists("/MrFTP/mock-uploaded-file.txt");
			Assertions.assertTrue(uploaded);
		}
	}
	
	@Test
	public void testUploadToPrivateDirectory() throws IOException
	{
		try (MockInputStream inputStream = new MockInputStream())
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
			client.download("/MrFTP/mock-file.txt", outputStream);
			
			byte[] expected = "Mock content".getBytes();
			byte[] actual = outputStream.toByteArray();
			
			Assertions.assertArrayEquals(expected, actual);
		}
	}
	
	@Test
	public void testDownloadNotExistingFile() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			Assertions.assertThrows(IOException.class, () -> {
				client.download("/MrFTP/not-existing-file.txt", outputStream);
			});
		}
	}
}