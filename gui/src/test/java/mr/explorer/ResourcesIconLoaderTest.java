package mr.explorer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

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
		InputStream inputStream = iconLoader.loadDirectoryIcon();
		
		Assertions.assertNotNull(inputStream);
	}
	
	@Test
	public void testFileIcon()
	{
		InputStream inputStream = iconLoader.loadFileIcon();
		
		Assertions.assertNotNull(inputStream);
	}
}