package mr.entry;

import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;
import mr.client.JschClientFactory;
import mr.server.MockSshServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JschEntriesProjectorTest
{
	private static final ClientFactory clientFactory = new JschClientFactory();
	
	private final Client client;
	
	public JschEntriesProjectorTest() throws ClientFactoryException
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
	public void testIsExistingFileShown() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = client.entriesProjector();
		entriesProjector.show("./src/test/resources", listEntriesView);
		
		Assertions.assertTrue(() -> (
			listEntriesView.isShown("existing-file")
		));
	}
	
	@Test
	public void testIsNotExistingFileNotShown() throws EntriesProjectionException
	{
		ListEntriesView listEntriesView = new ListEntriesView();
		
		EntriesProjector entriesProjector = client.entriesProjector();
		entriesProjector.show("./src/test/resources", listEntriesView);
		
		Assertions.assertFalse(() -> (
			listEntriesView.isShown("not-existing-file")
		));
	}
}