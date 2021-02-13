package mr.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JschClientFactory implements ClientFactory
{
	@Override
	public Client create(String hostname, int port, String username, String password) throws ClientFactoryException
	{
		try
		{
			JSch jsch = new JSch();
			jsch.setKnownHosts("./src/test/resources/hosts");
			jsch.addIdentity("./src/test/resources/key");
			
			Session session = jsch.getSession(username, hostname, port);
			session.setPassword(password);
			session.connect();
			
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			
			return new JschClient(channel);
		}
		catch (JSchException exception)
		{
			throw new ClientFactoryException(exception);
		}
	}
}