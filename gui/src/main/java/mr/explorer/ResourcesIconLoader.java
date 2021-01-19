package mr.explorer;

import java.io.InputStream;

public class ResourcesIconLoader implements IconLoader
{
	@Override
	public InputStream loadDirectoryIcon()
	{
		return getClass().getResourceAsStream("/directory.png");
	}
	
	@Override
	public InputStream loadFileIcon()
	{
		return getClass().getResourceAsStream("/file.png");
	}
}