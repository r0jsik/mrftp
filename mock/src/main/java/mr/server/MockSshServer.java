package mr.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.sshd.server.SshServer;

import mr.filesystem.director.FileSystemDirector;
import mr.filesystem.director.MockFileSystemDirector;
import mr.server.builder.ServerBuilder;
import mr.server.builder.SshdServerBuilder;

public class MockSshServer
{
	private static final FileSystemDirector fileSystemDirector = new MockFileSystemDirector();
	
	private static SshServer sshServer;
	private static Path directory;
	
	public static void start()
	{
		try
		{
			sshServer = SshServer.setUpDefaultServer();
			directory = Files.createTempDirectory("MrFTP");
			
			ServerBuilder serverBuilder = new SshdServerBuilder(sshServer, directory);
			serverBuilder.createFileSystem(fileSystemDirector);
			serverBuilder.createUser("MrFTP", "MrFTP");
			serverBuilder.initialize(7000);
			
			sshServer.start();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public static void close()
	{
		try
		{
			sshServer.stop();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public static boolean fileExists(String path)
	{
		return new File(directory.toString() + path).exists();
	}
}