package mr.mock;

import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;

public class MockClientFactory implements ClientFactory
{
	@Override
	public Client create(String hostname, int port, String username, String password) throws ClientFactoryException
	{
		if (username.equals("invalid"))
		{
			throw new ClientFactoryException();
		}
		
		return null;
	}
}