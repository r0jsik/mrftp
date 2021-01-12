package mr.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Client
{
	void upload(String path, InputStream inputStream) throws IOException;
	void download(String path, OutputStream outputStream) throws IOException;
}