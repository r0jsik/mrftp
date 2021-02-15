package mr.server;

import org.mockftpserver.fake.FakeFtpServer;

import mr.filesystem.director.FileSystemDirector;
import mr.filesystem.director.MockFileSystemDirector;
import mr.server.builder.FakeServerBuilder;
import mr.server.builder.ServerBuilder;

public class MockFtpServer
{
	private final static FakeFtpServer fakeFtpServer = new FakeFtpServer();
	private final static FileSystemDirector fileSystemDirector = new MockFileSystemDirector();
	
	public static void start()
	{
		ServerBuilder serverBuilder = new FakeServerBuilder(fakeFtpServer);
		serverBuilder.createFileSystem(fileSystemDirector);
		serverBuilder.createUser("MrFTP", "MrFTP");
		serverBuilder.initialize(7000);
		
		fakeFtpServer.start();
	}
	
	public static void close()
	{
		fakeFtpServer.stop();
	}
	
	public static boolean fileExists(String path)
	{
		return fakeFtpServer.getFileSystem().exists(path);
	}
}