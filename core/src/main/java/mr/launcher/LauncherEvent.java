package mr.launcher;

@FunctionalInterface
public interface LauncherEvent
{
	void call(String protocol, String hostname, int port, String username, String password, boolean remember);
}