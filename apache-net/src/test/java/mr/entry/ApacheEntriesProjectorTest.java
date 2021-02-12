package mr.entry;

import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;
import mr.client.InsecureApacheClientFactory;
import mr.server.MockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ApacheEntriesProjectorTest
{
	private static final ClientFactory clientFactory = new InsecureApacheClientFactory();
	
	private final Client client;
	
	public ApacheEntriesProjectorTest() throws ClientFactoryException
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
	public void testShowExistingFile() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		EntriesProjector entriesProjector = client.entriesProjector();
		
		entriesProjector.show("/MrFTP", listEntriesView);
		boolean shown = listEntriesView.isShown("existing-file");
		
		Assertions.assertTrue(shown);
	}
	
	@Test
	public void testShowNotExistingFile() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		EntriesProjector entriesProjector = client.entriesProjector();
		
		entriesProjector.show("/MrFTP", listEntriesView);
		boolean shown = listEntriesView.isShown("not-existing-file");
		
		Assertions.assertFalse(shown);
	}
}