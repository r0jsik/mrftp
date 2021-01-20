package mr.mock;

import org.mockftpserver.fake.FakeFtpServer;

public class MockServer
{
	private static final FakeFtpServer fakeFtpServer = new FakeFtpServer();
	
	public static void start()
	{
		FileSystemDirector fileSystemDirector = new SimpleFileSystemDirector();
		
		ServerBuilder serverBuilder = new FakeServerBuilder(fakeFtpServer);
		serverBuilder.createFileSystem(fileSystemDirector);
		serverBuilder.createUser("MrFTP", "MrFTP");
		
		fakeFtpServer.setServerControlPort(7000);
		fakeFtpServer.start();
	}
	
	public static void stop()
	{
		fakeFtpServer.stop();
	}
	
	public static boolean fileExists(String path)
	{
		return fakeFtpServer.getFileSystem().exists(path);
	}
}