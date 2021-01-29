package mr.transmitter;

import mr.client.Client;

public interface Transmitter
{
	void upload(Client client, String localPath, String remotePath);
	void download(Client client, String remotePath, String localPath);
}