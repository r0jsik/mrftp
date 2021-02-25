package mr.client;

import java.io.IOException;

@FunctionalInterface
public interface StreamProvider<T>
{
	T open(String path) throws IOException;
}