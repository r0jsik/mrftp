package mr.entry;

import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class TableEntriesController implements EntriesController
{
	private final TableView<TableEntryView> tableView;
	
	@Override
	public void setOnEnter(Consumer<String> callback)
	{
		tableView.setOnMousePressed(event -> {
			if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
			{
				withSelectedItem(callback);
			}
		});
	}
	
	private void withSelectedItem(Consumer<String> callback)
	{
		SelectionModel<TableEntryView> selectionModel = tableView.getSelectionModel();
		TableEntryView selectedItem = selectionModel.getSelectedItem();
		
		if (selectedItem != null)
		{
			callback.accept(selectedItem.getName());
		}
	}
}