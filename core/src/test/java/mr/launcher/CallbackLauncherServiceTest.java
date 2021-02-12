package mr.launcher;

import mr.client.ClientFactory;
import mr.client.MockClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class CallbackLauncherServiceTest
{
	private static final ClientFactory clientFactory = new MockClientFactory();
	
	@Test
	public void testCreateWithSuccess()
	{
		AtomicBoolean clientAccepted = new AtomicBoolean();
		CallbackLauncherService callbackLauncherService = new CallbackLauncherService(clientFactory);
		
		callbackLauncherService.setOnSuccess(client -> {
			clientAccepted.set(true);
		});
		
		callbackLauncherService.launch("", 0, "", "");
		
		Assertions.assertTrue(clientAccepted.get());
	}
	
	@Test
	public void testCreateWithFailure()
	{
		AtomicBoolean exceptionAccepted = new AtomicBoolean();
		CallbackLauncherService callbackLauncherService = new CallbackLauncherService(clientFactory);
		
		callbackLauncherService.setOnFailure(exception -> {
			exceptionAccepted.set(true);
		});
		
		callbackLauncherService.launch("", 0, "invalid", "");
		
		Assertions.assertTrue(exceptionAccepted.get());
	}
}