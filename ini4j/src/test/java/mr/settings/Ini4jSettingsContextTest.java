package mr.settings;

import org.ini4j.Ini;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Ini4jSettingsContextTest
{
	private final Ini ini;
	private final Settings settings;
	
	public Ini4jSettingsContextTest() throws IOException
	{
		Path path = Files.createTempFile(".settings", "");
		File file = path.toFile();
		file.deleteOnExit();
		
		ini = new Ini(file);
		settings = new Ini4jSettings(ini);
	}
	
	@Test
	public void testProtocol()
	{
		testSetterAndGetter(SettingsContext::setProtocol, "mock-protocol", SettingsContext::getProtocol);
	}
	
	private <T> void testSetterAndGetter(BiConsumer<SettingsContext, T> setter, T expectedValue, Function<SettingsContext, T> getter)
	{
		settings.update(context -> {
			setter.accept(context, expectedValue);
		});
		
		settings.select(context -> {
			Assertions.assertEquals(expectedValue, getter.apply(context));
		});
	}
	
	@Test
	public void testHostname()
	{
		testSetterAndGetter(SettingsContext::setHostname, "mock-hostname", SettingsContext::getHostname);
	}
	
	@Test
	public void testPort()
	{
		testSetterAndGetter(SettingsContext::setPort, 123456789, SettingsContext::getPort);
	}
	
	@Test
	public void testUsername()
	{
		testSetterAndGetter(SettingsContext::setUsername, "mock-username", SettingsContext::getUsername);
	}
	
	@Test
	public void testPassword()
	{
		testSetterAndGetter(SettingsContext::setPassword, "mock-password", SettingsContext::getPassword);
	}
}