package mr.server.builder;

import mr.filesystem.director.FileSystemDirector;

public interface ServerBuilder
{
	void createFileSystem(FileSystemDirector fileSystemDirector);
	void createUser(String username, String password);
	void initialize(int port);
}