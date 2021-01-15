package mr.explorer;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mr.ftp.entry.Entry;

import java.util.Iterator;

public class StageExplorerController implements ExplorerController
{
	@FXML
	private TableView<Entry> remoteView;
	
	@FXML
	private TableView<Entry> localView;
	
	@FXML
	private void initialize()
	{
		initialize(remoteView);
		initialize(localView);
	}
	
	private void initialize(TableView<Entry> tableView)
	{
		Iterator<TableColumn<Entry, ?>> iterator = tableView.getColumns().iterator();
		
		for (int i = 0; iterator.hasNext(); i++)
		{
			final int index = i;
			
			iterator.next().setCellValueFactory(callback -> EntryMapper.map(callback.getValue(), index));
		}
	}
	
	@Override
	public void showRemote(Entry entry)
	{
		remoteView.getItems().add(entry);
	}
	
	@Override
	public void hideRemote(Entry entry)
	{
		remoteView.getItems().remove(entry);
	}
	
	@Override
	public void showLocal(Entry entry)
	{
		localView.getItems().add(entry);
	}
	
	@Override
	public void hideLocal(Entry entry)
	{
		localView.getItems().remove(entry);
	}
}