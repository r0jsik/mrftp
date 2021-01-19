package mr.explorer;

import java.io.InputStream;

public interface IconLoader
{
	InputStream loadDirectoryIcon();
	InputStream loadFileIcon();
}