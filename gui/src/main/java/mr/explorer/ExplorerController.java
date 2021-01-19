package mr.explorer;

import mr.ftp.entry.Entry;

public interface ExplorerController
{
	void showStatus(String status);
	void showRemote(Entry entry);
	void hideRemote(Entry entry);
	void showLocal(Entry entry);
	void hideLocal(Entry entry);
}