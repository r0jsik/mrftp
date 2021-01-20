package mr.entry;

import java.io.IOException;

public interface EntriesProjector
{
	void show(String path, EntriesView entriesView) throws IOException;
}