package mr.mock;

import mr.entry.EntriesView;

import java.util.ArrayList;
import java.util.List;

public class MockEntriesView implements EntriesView
{
	private final List<String> entryNames;
	
	public MockEntriesView()
	{
		this.entryNames = new ArrayList<>();
	}
	
	@Override
	public void showFile(String name, long size)
	{
		entryNames.add(name);
	}
	
	@Override
	public void showDirectory(String name, long size)
	{
		entryNames.add(name);
	}
	
	public boolean isShown(String name)
	{
		return entryNames.stream().anyMatch(name::equals);
	}
}