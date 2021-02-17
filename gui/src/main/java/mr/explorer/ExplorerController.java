package mr.explorer;

public interface ExplorerController
{
	void showStatus(String status);
	void setRefreshLabel(String label);
	void setCloseLabel(String label);
	void setOnRefresh(Runnable runnable);
	void setOnClose(Runnable runnable);
}