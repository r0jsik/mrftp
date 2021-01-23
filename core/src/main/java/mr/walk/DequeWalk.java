package mr.walk;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;

@RequiredArgsConstructor
public class DequeWalk implements Walk
{
	private final Deque<CharSequence> path;
	
	public DequeWalk()
	{
		path = new ArrayDeque<>(Collections.singletonList("."));
	}
	
	@Override
	public void to(String entry)
	{
		if (entry.equals(".."))
		{
			path.removeLast();
		}
		else
		{
			path.addLast(entry);
		}
	}
	
	@Override
	public String toString()
	{
		return String.join("/", path);
	}
}