package mr.client;

public interface ClientFactory
{
	Client create(String host, int port, String username, String password);
}