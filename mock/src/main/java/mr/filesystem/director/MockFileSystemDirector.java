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
		
		fileSystemBuilder.createDirectory("/public-download-dir");
		fileSystemBuilder.createFile("/public-download-dir/a.txt", "A");
		fileSystemBuilder.createFile("/public-download-dir/b.txt", "B");
		fileSystemBuilder.createFile("/public-download-dir/c.txt", "C");
		fileSystemBuilder.createDirectory("/public-remove-dir");
		
		fileSystemBuilder.createInaccessibleDirectory("/private-download-dir");
		fileSystemBuilder.createFile("/private-download-dir/a.txt", "A");
		fileSystemBuilder.createFile("/private-download-dir/b.txt", "B");
		fileSystemBuilder.createFile("/private-download-dir/c.txt", "C");
	}
}