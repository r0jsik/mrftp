package mr.entry;

import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class TableEntriesController implements EntriesController
{
	private final TableView<TableEntryView> tableView;
	private final Button transmitFileButton;
	private final Button removeFileButton;
	
	@Override
	public void setOnEntryOpened(Consumer<String> callback)
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
	
	@Override
	public void setOnEntryTransmitted(Consumer<String> callback)
	{
		transmitFileButton.setOnAction(event -> {
			withSelectedItem(callback);
		});
	}
	
	@Override
	public void setOnEntryDeleted(Consumer<String> callback)
	{
		removeFileButton.setOnAction(event -> {
			withSelectedItem(callback);
		});
	}
}