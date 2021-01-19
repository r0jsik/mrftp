package mr.explorer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import lombok.RequiredArgsConstructor;
import mr.ftp.entry.Entry;

import java.io.InputStream;

@RequiredArgsConstructor
public class StageExplorerController implements ExplorerController
{
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private TableView<EntryView> remoteView;
	
	@FXML
	private TableView<EntryView> localView;
	
	@FXML
	private Label statusLabel;
	
	private final IconLoader iconLoader;
	
	@FXML
	private void initialize()
	{
		bindSplitPaneHeight();
	}
	
	private void bindSplitPaneHeight()
	{
		Region root = (Region) splitPane.getParent();
		ReadOnlyDoubleProperty rootHeight = root.heightProperty();
		DoubleProperty paneHeight = splitPane.prefHeightProperty();
		
		paneHeight.bind(rootHeight);
	}
	
	@Override
	public void showStatus(String status)
	{
		statusLabel.setText(status);
	}
	
	@Override
	public void showRemote(Entry entry)
	{
		show(remoteView, entry);
	}
	
	private void show(TableView<EntryView> tableView, Entry entry)
	{
		ImageView icon = loadIconOf(entry);
		
		EntryView entryView = new EntryView();
		entryView.setIcon(icon);
		entryView.setName(entry.getName());
		entryView.setSize(entry.getSize() + " B");
		
		tableView.getItems().add(entryView);
	}
	
	private ImageView loadIconOf(Entry entry)
	{
		InputStream inputStream;
		
		if (entry.isDirectory())
		{
			inputStream = iconLoader.loadDirectoryIcon();
		}
		else
		{
			inputStream = iconLoader.loadFileIcon();
		}
		
		Image image = new Image(inputStream);
		
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		
		return imageView;
	}
	
	@Override
	public void hideRemote(Entry entry)
	{
		hide(remoteView, entry);
	}
	
	private void hide(TableView<EntryView> tableView, Entry entry)
	{
		tableView.getItems().removeIf(entryView -> entryView.refersTo(entry));
	}
	
	@Override
	public void showLocal(Entry entry)
	{
		show(localView, entry);
	}
	
	@Override
	public void hideLocal(Entry entry)
	{
		hide(localView, entry);
	}
}