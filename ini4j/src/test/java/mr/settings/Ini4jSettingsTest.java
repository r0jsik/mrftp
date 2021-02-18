package mr.settings;

import org.ini4j.Ini;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ini4jSettingsTest
{
	private final Ini ini;
	private final Settings settings;
	
	public Ini4jSettingsTest() throws IOException
	{
		Path path = Files.createTempFile(".settings", "");
		File file = path.toFile();
		file.deleteOnExit();
		
		ini = new Ini(file);
		settings = new Ini4jSettings(ini);
	}
	
	@Test
	public void testUpdateSavesToFile() throws IOException
	{
		String expectedValue = "mock-value";
		
		settings.update(context -> {
			ini.put("test", "transaction", expectedValue);
		});
		
		File file = ini.getFile();
		Ini ini = new Ini(file);
		String actualValue = ini.get("test", "transaction");
		
		Assertions.assertEquals(expectedValue, actualValue);
	}
}