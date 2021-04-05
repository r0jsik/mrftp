package mr.server.builder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.subsystem.SubsystemFactory;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;

import mr.filesystem.builder.FileSystemBuilder;
import mr.filesystem.builder.NativeFileSystemBuilder;
import mr.filesystem.director.FileSystemDirector;

@RequiredArgsConstructor
public class SshdServerBuilder implements ServerBuilder
{
	private final SshServer sshServer;
	private final Path home;
	
	@Override
	public void createFileSystem(FileSystemDirector fileSystemDirector)
	{
		VirtualFileSystemFactory virtualFileSystemFactory = new VirtualFileSystemFactory();
		virtualFileSystemFactory.setDefaultHomeDir(home);
		
		FileSystemBuilder fileSystemBuilder = new NativeFileSystemBuilder(home);
		fileSystemDirector.build(fileSystemBuilder);
		
		sshServer.setFileSystemFactory(virtualFileSystemFactory);
	}
	
	@Override
	public void createUser(String username, String password)
	{
		sshServer.setPasswordAuthenticator((providedUsername, providedPassword, session) -> providedUsername.equals(username) && providedPassword.equals(password));
	}
	
	@Override
	public void initialize(int port)
	{
		Path pathToKeyFile = Paths.get("./src/test/resources/key");
		KeyPairProvider keyPairProvider = new FileKeyPairProvider(pathToKeyFile);
		SftpSubsystemFactory subsystemFactory = new SftpSubsystemFactory();
		List<SubsystemFactory> subsystemFactories = Collections.singletonList(subsystemFactory);
		
		sshServer.setPort(port);
		sshServer.setKeyPairProvider(keyPairProvider);
		sshServer.setSubsystemFactories(subsystemFactories);
	}
}