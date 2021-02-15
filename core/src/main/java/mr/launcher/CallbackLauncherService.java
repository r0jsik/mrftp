package mr.launcher;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Setter
public class CallbackLauncherService implements LauncherService
{
	private final ClientFactory sshClientFactory;
	private final ClientFactory ftpClientFactory;
	
	private Consumer<Client> onSuccess;
	private Consumer<ClientFactoryException> onFailure;
	
	@Override
	public void launch(String protocol, String hostname, int port, String username, String password)
	{
		try
		{
			ClientFactory clientFactory = getClientFactory(protocol);
			Client client = clientFactory.create(hostname, port, username, password);
			
			onSuccess.accept(client);
		}
		catch (ClientFactoryException exception)
		{
			onFailure.accept(exception);
		}
	}
	
	private ClientFactory getClientFactory(String protocol)
	{
		switch (protocol)
		{
			case "SFTP":
				return sshClientFactory;
			case "FTP":
				return ftpClientFactory;
			default:
				throw new IllegalArgumentException();
		}
	}
}