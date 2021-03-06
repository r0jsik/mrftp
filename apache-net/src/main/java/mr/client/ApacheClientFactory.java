package mr.client;

import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;

@RequiredArgsConstructor
public class ApacheClientFactory implements ClientFactory
{
	private final boolean secure;
	
	@Override
	public Client create(String host, int port, String username, String password)
	{
		try
		{
			return tryToCreate(host, port, username, password);
		}
		catch (IOException | IllegalArgumentException exception)
		{
			throw new ClientFactoryException(exception);
		}
	}
	
	private Client tryToCreate(String host, int port, String username, String password) throws IOException
	{
		FTPClient ftpClient = createFtpClient();
		ftpClient.connect(host, port);
		
		if (ftpClient.login(username, password))
		{
			return new ApacheClient(ftpClient);
		}
		else
		{
			throw new ClientFactoryException();
		}
	}
	
	private FTPClient createFtpClient()
	{
		if (secure)
		{
			return new FTPSClient();
		}
		else
		{
			return new FTPClient();
		}
	}
}