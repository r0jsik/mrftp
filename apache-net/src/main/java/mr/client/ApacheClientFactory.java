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
			FTPClient ftpClient = createFtpClient();
			ftpClient.connect(hostname, port);
			ftpClient.login(username, password);
			
			return new ApacheClient(ftpClient);
		}
		catch (IOException exception)
		{
			throw new ClientFactoryException(exception);
		}
	}
	
	protected FTPClient createFtpClient()
	{
		return new FTPSClient();
	}
}