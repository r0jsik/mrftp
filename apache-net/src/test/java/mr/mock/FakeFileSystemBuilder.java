package mr.mock;

import lombok.RequiredArgsConstructor;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;

@RequiredArgsConstructor
public class FakeFileSystemBuilder implements FileSystemBuilder
{
	private final FileSystem fileSystem;
	
	@Override
	public void createDirectory(String path)
	{
		DirectoryEntry directoryEntry = new DirectoryEntry();
		directoryEntry.setPath(path);
		
		fileSystem.add(directoryEntry);
	}
	
	@Override
	public void createFile(String path)
	{
		createFile(path, "");
	}
	
	@Override
	public void createFile(String path, String content)
	{
		FileEntry fileEntry = new FileEntry();
		fileEntry.setPath(path);
		fileEntry.setContents(content);
		
		fileSystem.add(fileEntry);
	}
}