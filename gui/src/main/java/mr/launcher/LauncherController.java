package mr.launcher;

public interface LauncherController
{
	void setAvailableProtocols(String... protocols);
	void setProtocol(String protocol);
	void setHost(String host);
	void setPort(int port);
	void setUsername(String username);
	void setPassword(String password);
	void setOnLaunched(LauncherEvent launcherEvent);
	void setOnRemember(LauncherEvent launcherEvent);
}