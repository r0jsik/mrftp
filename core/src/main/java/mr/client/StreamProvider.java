package mr.client;

import java.io.IOException;

@FunctionalInterface
public interface StreamProvider<T>
{
	void provide(T stream) throws IOException;
}