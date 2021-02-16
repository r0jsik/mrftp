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
	public void out()
	{
		walk.out();
	}
}