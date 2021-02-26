package mr.client;

import mr.server.MockSshServer;
import mr.stream.MockInputStream;
import mr.stream.MockOutputStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JschClientTest
{
	private static final ClientFactory clientFactory = new JschClientFactory("./src/test/resources/hosts");
	
	private final Client client;
	
	public JschClientTest()
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
			Assertions.assertThrows(ClientActionException.class, () -> {
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
	public void testRemove()
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
		Assertions.assertThrows(ClientActionException.class, () -> {
			client.remove("/public/not-existing-file");
		});
	}
	
	@Test
	public void testActionOnClosedClient() throws IOException
	{
		client.close();
		
		try (MockInputStream inputStream = new MockInputStream("Close test"))
		{
			Assertions.assertThrows(ClientActionException.class, () -> {
				client.upload("/public/close.txt", inputStream);
			});
		}
	}
	
	@Test
	public void testRemoveDirectory()
	{
		client.remove("/public-remove-dir");
		
		Assertions.assertFalse(() -> (
			MockSshServer.fileExists("/public-remove-dir")
		));
	}
	
	@Test
	public void testWalkDirectory()
	{
		List<String> resolvedPaths = new ArrayList<>();
		
		client.walk("/walk", resolvedPaths::add);
		
		List<String> expectedPaths = Arrays.asList(
			"walk/file-A",
			"walk/file-B",
			"walk/file-C",
			"walk/walk-P/file-D",
			"walk/walk-P/file-E",
			"walk/walk-Q/file-F",
			"walk/walk-Q/walk-R/file-G",
			"walk/walk-Q/walk-R/file-H",
			"walk/walk-Q/walk-R/file-I"
		);
		
		Collections.sort(resolvedPaths);
		Assertions.assertIterableEquals(expectedPaths, resolvedPaths);
	}
	
	@Test
	public void testWalkNestedDirectory()
	{
		List<String> resolvedPaths = new ArrayList<>();
		
		client.walk("/walk/walk-Q", resolvedPaths::add);
		
		List<String> expectedPaths = Arrays.asList(
			"walk-Q/file-F",
			"walk-Q/walk-R/file-G",
			"walk-Q/walk-R/file-H",
			"walk-Q/walk-R/file-I"
		);
		
		Collections.sort(resolvedPaths);
		Assertions.assertIterableEquals(expectedPaths, resolvedPaths);
	}
	
	@Test
	public void testWalkFile()
	{
		AtomicReference<String> resolvedPath = new AtomicReference<>();
		
		client.walk("/walk/file-B", resolvedPath::set);
		
		Assertions.assertEquals("file-B", resolvedPath.get());
	}
}