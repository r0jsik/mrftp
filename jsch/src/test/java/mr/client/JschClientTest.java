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
		this.client = clientFactory.create("localhost", 7000, "MrFTP", "MrFTP");
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
		try (MockInputStream inputStream = new MockInputStream())
		{
			client.upload("./src/test/resources/upload-test.txt", inputStream);
			
			boolean uploaded = MockSshServer.fileExists("./src/test/resources/upload-test.txt");
			Assertions.assertTrue(uploaded);
		}
	}
	
	@Test
	public void testDownload() throws IOException
	{
		try (MockOutputStream outputStream = new MockOutputStream())
		{
			client.download("./src/test/resources/download-test.txt", outputStream);
			
			byte[] expected = "Mock content".getBytes();
			byte[] actual = outputStream.toByteArray();
			
			Assertions.assertArrayEquals(expected, actual);
		}
	}
}