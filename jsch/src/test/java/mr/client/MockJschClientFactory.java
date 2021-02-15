package mr.client;

public class MockJschClientFactory extends JschClientFactory
{
	public MockJschClientFactory()
	{
		super("./src/test/resources/hosts", "./src/test/resources/key", null);
	}
}