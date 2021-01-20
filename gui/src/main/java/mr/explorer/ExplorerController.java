package mr.explorer;

import mr.entry.EntriesView;

public interface ExplorerController
{
	void showStatus(String status);
	EntriesView remoteEntriesView();
	EntriesView localEntriesView();
}