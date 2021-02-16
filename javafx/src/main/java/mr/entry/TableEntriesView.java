package mr.entry;

import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TableEntriesView implements EntriesView
{
	private final Image fileIcon;
	private final Image directoryIcon;
	private final TableView<TableEntryView> tableView;
	
	@Override
	public void showFile(String name, long size)
	{
		show(fileIcon, name, size);
	}
	
	private void show(Image image, String name, long size)
	{
		ImageView imageView = new ImageView(image);
		
		TableEntryView tableEntryView = new TableEntryView();
		tableEntryView.setIcon(imageView);
		tableEntryView.setName(name);
		tableEntryView.setSize(size + " B");
		
		tableView.getItems().add(tableEntryView);
	}
	
	@Override
	public void showDirectory(String name, long size)
	{
		show(directoryIcon, name, size);
	}
	
	@Override
	public void hideAll()
	{
		tableView.getItems().clear();
		
		Platform.runLater(() -> {
			tableView.sort();
		});
	}
}