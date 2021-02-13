package mr.server;

import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.shell.ProcessShellCommandFactory;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

public class MockSshServer
{
	private static SshServer sshServer;
	
	public static void start()
	{
		try
		{
			sshServer = SshServer.setUpDefaultServer();
			sshServer.setPort(7000);
			sshServer.setHost("127.0.0.1");
			sshServer.setKeyPairProvider(new FileKeyPairProvider(Paths.get("./src/test/resources/key")));
			sshServer.setPasswordAuthenticator((username, password, session) -> username.equals("MrFTP") && password.equals("MrFTP"));
			sshServer.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));
			sshServer.setCommandFactory(new ProcessShellCommandFactory());
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
		return new File(path).exists();
	}
}