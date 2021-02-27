package mr.client;

import mr.entry.EntriesProjector;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public interface Client
{
	void write(String path, StreamProvider<OutputStream> callback);
	void read(String path, StreamProvider<InputStream> callback);
	void remove(String path);
	void walk(String from, String entry, Consumer<String> callback);
	void close();
	EntriesProjector entriesProjector();
}