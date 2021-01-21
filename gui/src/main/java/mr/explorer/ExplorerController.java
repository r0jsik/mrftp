package mr.explorer;

import mr.entry.EntriesController;
import mr.entry.EntriesView;

public interface ExplorerController
{
	void showStatus(String status);
	EntriesView remoteEntriesView();
	EntriesController remoteEntriesController();
	EntriesView localEntriesView();
	EntriesController localEntriesController();
}