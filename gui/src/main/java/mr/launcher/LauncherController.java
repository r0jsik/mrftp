package mr.launcher;

public interface LauncherController
{
	void setAvailableProtocols(String... protocols);
	void setProtocol(String protocol);
	void setHostname(String hostname);
	void setPort(int port);
	void setUsername(String username);
	void setPassword(String password);
	void setProtocolLabel(String label);
	void setHostnameLabel(String label);
	void setPortLabel(String label);
	void setUsernameLabel(String label);
	void setPasswordLabel(String label);
	void setSettingsLabel(String label);
	void setStartLabel(String label);
}