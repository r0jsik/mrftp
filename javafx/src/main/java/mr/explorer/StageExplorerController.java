package mr.explorer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import lombok.RequiredArgsConstructor;
import mr.entry.EntriesController;
import mr.entry.TableEntriesController;
import mr.entry.TableEntriesView;
import mr.entry.TableEntryView;

@RequiredArgsConstructor
public class StageExplorerController implements ExplorerController
{
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private TableView<TableEntryView> remoteView;
	
	@FXML
	private Button downloadButton;
	
	@FXML
	private TableView<TableEntryView> localView;
	
	@FXML
	private Button uploadButton;
	
	@FXML
	private Label statusLabel;
	
	private final IconLoader iconLoader;
	private final ExplorerService explorerService;
	
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
	
	@FXML
	private void close()
	{
		explorerService.close();
	}
	
	@FXML
	private void refresh()
	{
		explorerService.refresh();
	}
	
	@Override
	public void showStatus(String status)
	{
		statusLabel.setText(status);
	}
	
	public TableEntriesView remoteEntriesView()
	{
		return new TableEntriesView(iconLoader, remoteView);
	}
	
	public EntriesController remoteEntriesController()
	{
		return new TableEntriesController(remoteView, downloadButton);
	}
	
	public TableEntriesView localEntriesView()
	{
		return new TableEntriesView(iconLoader, localView);
	}
	
	public EntriesController localEntriesController()
	{
		return new TableEntriesController(localView, uploadButton);
	}
}