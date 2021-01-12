package mr.mock;

public interface ServerBuilder
{
	void createFileSystem(FileSystemDirector fileSystemDirector);
	void createUser(String username, String password);
}