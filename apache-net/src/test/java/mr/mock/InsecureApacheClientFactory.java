package mr.mock;

import mr.ftp.ApacheClientFactory;
import org.apache.commons.net.ftp.FTPClient;

public class InsecureApacheClientFactory extends ApacheClientFactory
{
	@Override
	protected FTPClient createFtpClient()
	{
		return new FTPClient();
	}
}