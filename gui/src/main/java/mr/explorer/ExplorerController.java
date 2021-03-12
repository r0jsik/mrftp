package mr.explorer;

public interface ExplorerController
{
	void setStatus(String status);
	void setOnRefresh(Runnable runnable);
	void setOnClose(Runnable runnable);
}