package mr.client;

public interface ClientFactory
{
	Client create(String hostname, int port, String username, String password) throws ClientFactoryException;
}