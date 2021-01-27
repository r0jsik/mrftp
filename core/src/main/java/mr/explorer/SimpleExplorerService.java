package mr.explorer;

import lombok.Setter;

@Setter
public class SimpleExplorerService implements ExplorerService
{
	private Runnable onRefresh;
	private Runnable onClose;
	
	@Override
	public void refresh()
	{
		onRefresh.run();
	}
	
	@Override
	public void close()
	{
		onClose.run();
	}
}