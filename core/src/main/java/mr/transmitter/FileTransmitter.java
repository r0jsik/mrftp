package mr.transmitter;

import mr.client.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTransmitter implements Transmitter
{
	@Override
	public void upload(Client client, String localPath, String remotePath)
	{
		try
		{
			File file = new File(localPath);
			FileInputStream fileInputStream = new FileInputStream(file);
			
			client.upload(remotePath, fileInputStream);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	@Override
	public void download(Client client, String remotePath, String localPath)
	{
		try
		{
			File file = new File(localPath);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			
			client.download(remotePath, fileOutputStream);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
}