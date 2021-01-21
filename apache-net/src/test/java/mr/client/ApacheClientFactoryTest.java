package mr.client;

import mr.mock.MockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ApacheClientFactoryTest
{
	private static final ClientFactory clientFactory = new InsecureApacheClientFactory();
	
	@BeforeAll
	public static void startServer()
	{
		MockServer.start();
	}
	
	@AfterAll
	public static void closeServer()
	{
		MockServer.stop();
	}
	
	@Test
	public void testCreate() throws ClientFactoryException
	{
		Client client = clientFactory.create("localhost", 7000, "MrFTP", "MrFTP");
		Assertions.assertNotNull(client);
	}
	
	@Test
	public void testCreateUsingInvalidServer()
	{
		Assertions.assertThrows(ClientFactoryException.class, () -> {
			clientFactory.create("invalid-server", 7000, "MrFTP", "MrFTP");
		});
	}
	
	@Test
	public void testCreateUsingInvalidCredentials()
	{
		Assertions.assertThrows(ClientFactoryException.class, () -> {
			clientFactory.create("localhost", 7000, "Invalid user", "MrFTP");
		});
	}
}