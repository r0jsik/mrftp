package mr.client;

import org.apache.commons.net.ftp.FTPClient;

public class InsecureApacheClientFactory extends ApacheClientFactory
{
	@Override
	protected FTPClient createFtpClient()
	{
		return new FTPClient();
	}
}