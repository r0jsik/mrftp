package mr.ftp;

import mr.ftp.entry.Entry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public interface Client
{
	void upload(String path, InputStream inputStream) throws IOException;
	void download(String path, OutputStream outputStream) throws IOException;
	void forEach(String path, Consumer<Entry> callback) throws IOException;
}