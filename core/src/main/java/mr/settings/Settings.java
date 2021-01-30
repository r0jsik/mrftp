package mr.settings;

public interface Settings
{
	void setHostname(String value);
	void setPort(int value);
	void setUsername(String value);
	void setPassword(String value);
	String getHostname();
	int getPort();
	String getUsername();
	String getPassword();
	void commit();
}