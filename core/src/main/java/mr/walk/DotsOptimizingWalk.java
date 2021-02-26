package mr.walk;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DotsOptimizingWalk implements Walk
{
	private final Walk walk;
	
	@Override
	public void to(String entry)
	{
		if (entry.equals(".."))
		{
			walk.out();
		}
		else if ( !entry.equals("."))
		{
			walk.to(entry);
		}
	}
	
	@Override
	public String resolve(String path)
	{
		return walk.resolve(path);
	}
	
	@Override
	public String relate(String path)
	{
		return walk.relate(path);
	}
	
	@Override
	public void out()
	{
		walk.out();
	}
	
	@Override
	public void home()
	{
		walk.home();
	}
	
	@Override
	public String toString()
	{
		return walk.toString();
	}
}