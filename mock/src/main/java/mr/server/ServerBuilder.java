package mr.server;

import mr.filesystem.FileSystemDirector;

public interface ServerBuilder
{
	void createFileSystem(FileSystemDirector fileSystemDirector);
	void createUser(String username, String password);
}