package mr.client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StaticClientFactoryProvider implements ClientFactoryProvider
{
	private final ClientFactory sshClientFactory;
	private final ClientFactory ftpClientFactory;
	private final ClientFactory insecureFtpClientFactory;
	
	@Override
	public ClientFactory getByProtocol(String protocol)
	{
		switch (protocol)
		{
			case "SFTP":
				return sshClientFactory;
			case "FTP":
				return ftpClientFactory;
			default:
				return insecureFtpClientFactory;
		}
	}
}