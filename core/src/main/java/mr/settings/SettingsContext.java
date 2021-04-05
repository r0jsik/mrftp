package mr.settings;

public interface SettingsContext
{
	void setProtocol(String value);
	void setHost(String value);
	void setPort(int value);
	void setUsername(String value);
	void setPassword(String value);
	String getProtocol();
	String getHost();
	int getPort();
	String getUsername();
	String getPassword();
}