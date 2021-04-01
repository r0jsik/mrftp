package mr.client;

import org.junit.jupiter.api.Test;

public class FileClientTest
{
	private final Client client;
	
	public FileClientTest()
	{
		client = new FileClient();
	}
	
	@Test
	public void testWalk()
	{
		client.walk("./src/main/java", "mr", (relativePath, isDirectory) -> System.out.println(relativePath));
	}
}