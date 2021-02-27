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
		
		fileSystemBuilder.createDirectory("/walk");
		fileSystemBuilder.createDirectory("/walk/walk-P");
		fileSystemBuilder.createDirectory("/walk/walk-Q");
		fileSystemBuilder.createDirectory("/walk/walk-Q/walk-R");
		fileSystemBuilder.createFile("/walk/file-A");
		fileSystemBuilder.createFile("/walk/file-B");
		fileSystemBuilder.createFile("/walk/file-C");
		fileSystemBuilder.createFile("/walk/walk-P/file-D");
		fileSystemBuilder.createFile("/walk/walk-P/file-E");
		fileSystemBuilder.createFile("/walk/walk-Q/file-F");
		fileSystemBuilder.createFile("/walk/walk-Q/walk-R/file-G");
		fileSystemBuilder.createFile("/walk/walk-Q/walk-R/file-H");
		fileSystemBuilder.createFile("/walk/walk-Q/walk-R/file-I");
	}
}