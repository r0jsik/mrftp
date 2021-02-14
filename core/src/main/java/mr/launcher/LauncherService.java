package mr.launcher;

public interface LauncherService
{
	void launch(String protocol, String hostname, int port, String username, String password);
}