package mr.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

@RequiredArgsConstructor
public class SpringClientFactoryProvider implements ClientFactoryProvider
{
	private final ClientFactoryProvider clientFactoryProvider;
	private final AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	@Override
	public ClientFactory getByProtocol(String protocol)
	{
		return (host, port, username, password) -> {
			ClientFactory clientFactory = clientFactoryProvider.getByProtocol(protocol);
			Client client = clientFactory.create(host, port, username, password);
			
			autowireCapableBeanFactory.autowireBean(client);
			
			return client;
		};
	}
}