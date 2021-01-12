package mr.mock;

public interface FileSystemBuilder
{
	void createDirectory(String path);
	void createFile(String path);
	void createFile(String path, String content);
}