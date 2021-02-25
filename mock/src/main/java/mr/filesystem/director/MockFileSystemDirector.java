package mr.filesystem.director;

import mr.filesystem.builder.FileSystemBuilder;

public class MockFileSystemDirector implements FileSystemDirector
{
	@Override
	public void build(FileSystemBuilder fileSystemBuilder)
	{
		fileSystemBuilder.createDirectory("/public");
		fileSystemBuilder.createFile("/public/download.txt", "Download test");
		fileSystemBuilder.createFile("/public/remove.txt", "Remove test");
		fileSystemBuilder.createFile("/public/existing-file", "");
		fileSystemBuilder.createInaccessibleDirectory("/private");
		fileSystemBuilder.createFile("/private/auth", "secret-password");
		fileSystemBuilder.createDirectory("/public-remove-dir");
	}
}