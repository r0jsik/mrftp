package mr.launcher;

@FunctionalInterface
public interface LauncherEvent
{
	void call(String protocol, String host, int port, String username, String password);
}