package mr.entry;

import mr.client.ApacheClientFactory;
import mr.client.Client;
import mr.client.ClientFactory;
import mr.server.MockFtpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ApacheEntriesProjectorTest
{
	private static final ClientFactory clientFactory = new ApacheClientFactory(false);
	
	private final Client client;
	
	public ApacheEntriesProjectorTest()
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
	public void testIsExistingFileShown()
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = client.entriesProjector();
		entriesProjector.show("/public", listEntriesView);
		
		Assertions.assertTrue(() -> (
			listEntriesView.isShown("existing-file")
		));
	}
	
	@Test
	public void testIsNotExistingFileNotShown()
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = client.entriesProjector();
		entriesProjector.show("/public", listEntriesView);
		
		Assertions.assertFalse(() -> (
			listEntriesView.isShown("not-existing-file")
		));
	}
}