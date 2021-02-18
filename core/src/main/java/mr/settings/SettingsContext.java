package mr.settings;

public interface SettingsContext
{
	void setProtocol(String value);
	void setHostname(String value);
	void setPort(int value);
	void setUsername(String value);
	void setPassword(String value);
	String getProtocol();
	String getHostname();
	int getPort();
	String getUsername();
	String getPassword();
}