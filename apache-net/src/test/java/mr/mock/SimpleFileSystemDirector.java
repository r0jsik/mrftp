package mr.mock;

public class SimpleFileSystemDirector implements FileSystemDirector
{
	@Override
	public void build(FileSystemBuilder fileSystemBuilder)
	{
		fileSystemBuilder.createDirectory("/MrFTP");
		fileSystemBuilder.createFile("/MrFTP/mock-file.txt", "Mock content");
		fileSystemBuilder.createFile("/MrFTP/existing-file", "");
		fileSystemBuilder.createInaccessibleDirectory("/private");
	}
}