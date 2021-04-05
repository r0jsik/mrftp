package mr.settings;

import lombok.RequiredArgsConstructor;
import org.ini4j.Ini;

import java.io.IOException;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Ini4jSettings implements Settings
{
	private final Ini ini;
	
	@Override
	public void update(Consumer<SettingsContext> callback)
	{
		SettingsContext settingsContext = new Ini4jSettingsContext(ini);
		callback.accept(settingsContext);
		
		try
		{
			ini.store();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	@Override
	public void select(Consumer<SettingsContext> callback)
	{
		SettingsContext settingsContext = new Ini4jSettingsContext(ini);
		callback.accept(settingsContext);
	}
}