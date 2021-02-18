package mr.settings;

import lombok.RequiredArgsConstructor;
import org.ini4j.Ini;

@RequiredArgsConstructor
public class Ini4jSettingsContext implements SettingsContext
{
	private final Ini ini;
	
	@Override
	public void setProtocol(String value)
	{
		ini.put("launcher", "protocol", value);
	}
	
	@Override
	public void setHostname(String value)
	{
		ini.put("launcher", "hostname", value);
	}
	
	@Override
	public void setPort(int value)
	{
		ini.put("launcher", "port", value);
	}
	
	@Override
	public void setUsername(String value)
	{
		ini.put("launcher", "username", value);
	}
	
	@Override
	public void setPassword(String value)
	{
		ini.put("launcher", "password", value);
	}
	
	@Override
	public String getProtocol()
	{
		return ini.get("launcher", "protocol");
	}
	
	@Override
	public String getHostname()
	{
		return ini.get("launcher", "hostname");
	}
	
	@Override
	public int getPort()
	{
		return ini.get("launcher", "port", Integer.class);
	}
	
	@Override
	public String getUsername()
	{
		return ini.get("launcher", "username");
	}
	
	@Override
	public String getPassword()
	{
		return ini.get("launcher", "password");
	}
}