package mr.explorer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
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
	private Button closeButton;
	
	@FXML
	private Button refreshButton;
	
	@FXML
	private TableView<TableEntryView> remoteView;
	
	@FXML
	private Button transmitRemoteFileButton;
	
	@FXML
	private Button removeRemoteFileButton;
	
	@FXML
	private TableView<TableEntryView> localView;
	
	@FXML
	private Button transmitLocalFileButton;
	
	@FXML
	private Button removeLocalFileButton;
	
	@FXML
	private Label statusLabel;
	
	private final Image fileIcon;
	private final Image directoryIcon;
	
	@FXML
	private void initialize()
	{
		initDefaultEntryViewsSorting();
	}
	
	private void initDefaultEntryViewsSorting()
	{
		sortByColumn(localView, 1);
		sortByColumn(remoteView, 1);
	}
	
	private void sortByColumn(TableView<TableEntryView> tableView, int columnIndex)
	{
		tableView.getSortOrder().add(tableView.getColumns().get(columnIndex));
	}
	
	@Override
	public void setOnRefresh(Runnable runnable)
	{
		refreshButton.setOnAction(event -> runnable.run());
	}
	
	@Override
	public void setOnClose(Runnable runnable)
	{
		closeButton.setOnAction(event -> runnable.run());
	}
	
	@Override
	public void setRefreshLabel(String label)
	{
		refreshButton.setText(label);
	}
	
	@Override
	public void setCloseLabel(String label)
	{
		closeButton.setText(label);
	}
	
	@Override
	public void showStatus(String status)
	{
		statusLabel.setText(status);
	}
	
	public TableEntriesView remoteEntriesView()
	{
		return new TableEntriesView(fileIcon, directoryIcon, remoteView);
	}
	
	public EntriesController remoteEntriesController()
	{
		return new TableEntriesController(remoteView, transmitRemoteFileButton, removeRemoteFileButton);
	}
	
	public TableEntriesView localEntriesView()
	{
		return new TableEntriesView(fileIcon, directoryIcon, localView);
	}
	
	public EntriesController localEntriesController()
	{
		return new TableEntriesController(localView, transmitLocalFileButton, removeLocalFileButton);
	}
}