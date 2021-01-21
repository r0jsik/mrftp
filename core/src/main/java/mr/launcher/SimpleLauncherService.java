package mr.launcher;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mr.client.Client;
import mr.client.ClientFactory;
import mr.client.ClientFactoryException;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Setter
public class SimpleLauncherService implements LauncherService
{
	private final ClientFactory clientFactory;
	
	private Consumer<Client> onSuccess;
	private Consumer<ClientFactoryException> onFailure;
	
	@Override
	public void launch(String hostname, int port, String username, String password)
	{
		try
		{
			Client client = clientFactory.create(hostname, port, username, password);
			onSuccess.accept(client);
		}
		catch (ClientFactoryException exception)
		{
			onFailure.accept(exception);
		}
	}
}