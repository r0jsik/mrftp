package mr.client;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;

public class ApacheClientFactory implements ClientFactory
{
	@Override
	public Client create(String hostname, int port, String username, String password) throws ClientFactoryException
	{
		try
		{
			return tryToCreate(hostname, port, username, password);
		}
		catch (IOException exception)
		{
			throw new ClientFactoryException(exception);
		}
	}
	
	private Client tryToCreate(String hostname, int port, String username, String password) throws IOException, ClientFactoryException
	{
		FTPClient ftpClient = createFtpClient();
		ftpClient.connect(hostname, port);
		
		if (ftpClient.login(username, password))
		{
			return new ApacheClient(ftpClient);
		}
		else
		{
			throw new ClientFactoryException();
		}
	}
	
	protected FTPClient createFtpClient()
	{
		return new FTPSClient();
	}
}