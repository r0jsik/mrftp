package mr.explorer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourcesIconLoaderTest
{
	private final IconLoader iconLoader;
	
	public ResourcesIconLoaderTest()
	{
		this.iconLoader = new ResourcesIconLoader();
	}
	
	@Test
	public void testDirectoryIcon()
	{
		Assertions.assertNotNull(iconLoader.loadDirectoryIcon());
	}
	
	@Test
	public void testFileIcon()
	{
		Assertions.assertNotNull(iconLoader.loadFileIcon());
	}
}