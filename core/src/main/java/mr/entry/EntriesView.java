package mr.entry;

public interface EntriesView
{
	void showFile(String name, long size);
	void showDirectory(String name, long size);
	void hideAll();
}