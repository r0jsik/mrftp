package mr.ftp.entry;

import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;

@RequiredArgsConstructor
public class ApacheEntry implements Entry
{
	private final FTPFile ftpFile;
	
	@Override
	public String getName()
	{
		return ftpFile.getName();
	}
}