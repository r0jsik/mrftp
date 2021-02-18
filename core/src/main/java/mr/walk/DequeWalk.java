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
		path.addLast(entry);
	}
	
	@Override
	public String resolve(String entry)
	{
		return String.join("", this.toString(), "/", entry);
	}
	
	@Override
	public void out()
	{
		if (path.size() > 0)
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