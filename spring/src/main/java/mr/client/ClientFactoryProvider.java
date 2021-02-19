package mr.client;

public interface ClientFactoryProvider
{
	ClientFactory getByProtocol(String protocol);
}