package mr.launcher;

import lombok.RequiredArgsConstructor;
import mr.ftp.Client;
import mr.ftp.ClientFactory;
import mr.ftp.ClientFactoryException;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ClientLauncherService implements LauncherService
{
	private final ClientFactory clientFactory;
	private final Consumer<Client> onSuccess;
	private final Consumer<ClientFactoryException> onFailure;
	
	@Override
	public void launch(String address, int port, String username, String password)
	{
		try
		{
			Client client = clientFactory.create(address, port, username, password);
			onSuccess.accept(client);
		}
		catch (ClientFactoryException exception)
		{
			onFailure.accept(exception);
		}
	}
}