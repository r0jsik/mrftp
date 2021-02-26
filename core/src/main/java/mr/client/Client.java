package mr.client;

import mr.entry.EntriesProjector;

import java.io.InputStream;
import java.io.OutputStream;

public interface Client
{
	void upload(String path, InputStream inputStream);
	void download(String path, OutputStream outputStream);
	void remove(String path);
	void close();
	EntriesProjector entriesProjector();
}