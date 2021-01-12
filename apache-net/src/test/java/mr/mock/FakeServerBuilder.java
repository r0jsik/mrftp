package mr.mock;

import lombok.RequiredArgsConstructor;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

@RequiredArgsConstructor
public class FakeServerBuilder implements ServerBuilder
{
	private final FakeFtpServer fakeFtpServer;
	
	@Override
	public void createFileSystem(FileSystemDirector fileSystemDirector)
	{
		FileSystem fileSystem = new UnixFakeFileSystem();
		FileSystemBuilder fileSystemBuilder = new FakeFileSystemBuilder(fileSystem);
		fileSystemDirector.build(fileSystemBuilder);
		
		fakeFtpServer.setFileSystem(fileSystem);
	}
	
	@Override
	public void createUser(String username, String password)
	{
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(username);
		userAccount.setPassword(password);
		userAccount.setHomeDirectory("/" + username);
		
		fakeFtpServer.addUserAccount(userAccount);
	}
}