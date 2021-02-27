package mr.walk;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;

@RequiredArgsConstructor
public class DequeWalk implements Walk
{
	private final Deque<CharSequence> path;
	
	public DequeWalk()
	{
		path = new ArrayDeque<>();
	}
	
	@Override
	public void to(String entry)
	{
		if (entry.equals(".."))
		{
			out();
		}
		else if ( !entry.equals("."))
		{
			path.addLast(entry);
		}
	}
	
	@Override
	public String resolve(String path)
	{
		return String.join("/", toString(), path);
	}
	
	@Override
	public String relate(String path)
	{
		return String.join("/", path, toString());
	}
	
	@Override
	public void out()
	{
		if (path.size() > 1)
		{
			path.removeLast();
		}
	}
	
	@Override
	public void home()
	{
		path.clear();
		path.add(".");
	}
	
	@Override
	public String toString()
	{
		return String.join("/", path);
	}
}