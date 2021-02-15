package mr.client;

import mr.server.MockFtpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ApacheClientFactoryTest
{
	private static final ClientFactory clientFactory = new MockApacheClientFactory();
	
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
	public void testCreate()
	{
		Assertions.assertDoesNotThrow(() -> {
			clientFactory.create("localhost", 7000, "MrFTP", "MrFTP");
		});
	}
	
	@Test
	public void testCreateUsingInvalidHost()
	{
		Assertions.assertThrows(ClientFactoryException.class, () -> {
			clientFactory.create("!@#$%^&*", 7000, "MrFTP", "MrFTP");
		});
	}
	
	@Test
	public void testCreateUsingInvalidPort()
	{
		Assertions.assertThrows(ClientFactoryException.class, () -> {
			clientFactory.create("localhost", 98765, "MrFTP", "MrFTP");
		});
	}
	
	@Test
	public void testCreateUsingInvalidUsername()
	{
		Assertions.assertThrows(ClientFactoryException.class, () -> {
			clientFactory.create("localhost", 7000, "Invalid user", "MrFTP");
		});
	}
	
	@Test
	public void testCreateUsingInvalidPassword()
	{
		Assertions.assertThrows(ClientFactoryException.class, () -> {
			clientFactory.create("localhost", 7000, "MrFTP", "!@#$%^&*");
		});
	}
}