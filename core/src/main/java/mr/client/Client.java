package mr.client;

import mr.entry.EntriesProjector;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public interface Client
{
	void upload(String path, InputStream inputStream);
	void download(String path, OutputStream outputStream);
	void remove(String path);
	void walk(String path, Consumer<String> callback);
	void close();
	EntriesProjector entriesProjector();
}