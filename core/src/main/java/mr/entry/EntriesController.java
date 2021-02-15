package mr.entry;

import java.util.function.Consumer;

public interface EntriesController
{
	void setOnEntryOpened(Consumer<String> callback);
	void setOnEntryTransmitted(Consumer<String> callback);
	void setOnEntryDeleted(Consumer<String> callback);
}