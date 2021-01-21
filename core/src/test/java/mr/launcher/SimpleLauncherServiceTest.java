package mr.launcher;

import mr.client.ClientFactory;
import mr.mock.MockClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleLauncherServiceTest
{
	private static final ClientFactory clientFactory = new MockClientFactory();
	
	@Test
	public void testCreateValid()
	{
		AtomicBoolean clientAccepted = new AtomicBoolean();
		SimpleLauncherService simpleLauncherService = new SimpleLauncherService(clientFactory);
		
		simpleLauncherService.setOnSuccess(client -> {
			clientAccepted.set(true);
		});
		
		simpleLauncherService.launch("", 0, "", "");
		
		Assertions.assertTrue(clientAccepted.get());
	}
	
	@Test
	public void testCreateInvalid()
	{
		AtomicBoolean exceptionAccepted = new AtomicBoolean();
		SimpleLauncherService simpleLauncherService = new SimpleLauncherService(clientFactory);
		
		simpleLauncherService.setOnFailure(exception -> {
			exceptionAccepted.set(true);
		});
		
		simpleLauncherService.launch("", 0, "invalid", "");
		
		Assertions.assertTrue(exceptionAccepted.get());
	}
}