package mr.filesystem.builder;

public interface FileSystemBuilder
{
	void createDirectory(String path);
	void createInaccessibleDirectory(String path);
	void createFile(String path);
	void createFile(String path, String content);
}