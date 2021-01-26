package mr.entry;

import java.util.ArrayList;
import java.util.List;

public class ListEntriesView implements EntriesView
{
	private final List<String> entries;
	
	public ListEntriesView()
	{
		entries = new ArrayList<>();
	}
	
	@Override
	public void showFile(String name, long size)
	{
		entries.add(name);
	}
	
	@Override
	public void showDirectory(String name, long size)
	{
		entries.add(name);
	}
	
	@Override
	public void hideAll()
	{
		entries.clear();
	}
	
	public boolean isShown(String name)
	{
		return entries.stream().anyMatch(name::equals);
	}
}