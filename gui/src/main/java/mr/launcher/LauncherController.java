package mr.launcher;

public interface LauncherController
{
	void showAvailableProtocols(String... options);
	void setProtocol(String protocol);
	void setHostname(String hostname);
	void setPort(int port);
	void setUsername(String username);
	void setPassword(String password);
}