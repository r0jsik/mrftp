package mr.client;

import mr.entry.EntriesProjector;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Client extends Closeable
{
	void upload(String path, InputStream inputStream) throws IOException;
	void download(String path, OutputStream outputStream) throws IOException;
	void remove(String path) throws IOException;
	EntriesProjector entriesProjector();
}