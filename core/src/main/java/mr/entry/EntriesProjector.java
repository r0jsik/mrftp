package mr.entry;

public interface EntriesProjector
{
	void show(String path, EntriesView entriesView) throws EntriesProjectionException;
}