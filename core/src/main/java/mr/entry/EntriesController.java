package mr.entry;

import java.util.function.Consumer;

public interface EntriesController
{
	void setOnEnter(Consumer<String> callback);
}