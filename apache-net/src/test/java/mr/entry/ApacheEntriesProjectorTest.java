package mr.entry;

import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;
import mr.client.MockApacheClientFactory;
import mr.server.MockFtpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ApacheEntriesProjectorTest
{
	private static final ClientFactory clientFactory = new MockApacheClientFactory();
	
	private final Client client;
	
	public ApacheEntriesProjectorTest() throws ClientFactoryException
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
	public void testIsExistingFileShown() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = client.entriesProjector();
		entriesProjector.show("/public", listEntriesView);
		
		Assertions.assertTrue(() -> (
			listEntriesView.isShown("existing-file")
		));
	}
	
	@Test
	public void testIsNotExistingFileNotShown() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = client.entriesProjector();
		entriesProjector.show("/public", listEntriesView);
		
		Assertions.assertFalse(() -> (
			listEntriesView.isShown("not-existing-file")
		));
	}
}