package mr.filesystem;

public class SimpleFileSystemDirector implements FileSystemDirector
{
	@Override
	public void build(FileSystemBuilder fileSystemBuilder)
	{
		fileSystemBuilder.createDirectory("/public");
		fileSystemBuilder.createFile("/public/download.txt", "Download test");
		fileSystemBuilder.createFile("/public/existing-file", "");
		fileSystemBuilder.createInaccessibleDirectory("/private");
		fileSystemBuilder.createFile("/private/auth", "secret-password");
	}
}