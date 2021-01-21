package mr.entry;

import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;
import mr.explorer.IconLoader;

import java.io.InputStream;

@RequiredArgsConstructor
public class TableEntriesView implements EntriesView
{
	private final IconLoader iconLoader;
	private final TableView<TableEntryView> tableView;
	
	@Override
	public void showFile(String name, long size)
	{
		show(iconLoader.loadFileIcon(), name, size);
	}
	
	void show(InputStream iconInputStream, String name, long size)
	{
		Image image = new Image(iconInputStream);
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
		show(iconLoader.loadDirectoryIcon(), name, size);
	}
	
	@Override
	public void hideAll()
	{
		tableView.getItems().clear();
	}
}