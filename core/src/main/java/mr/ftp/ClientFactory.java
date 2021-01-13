package mr.ftp;

public interface ClientFactory
{
	Client create(String hostname, int port, String username, String password) throws ClientFactoryException;
}