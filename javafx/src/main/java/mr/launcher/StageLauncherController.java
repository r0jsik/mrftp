package mr.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class StageLauncherController implements LauncherController
{
	@FXML
	private ComboBox<String> protocolField;
	
	@FXML
	private TextField hostField;
	
	@FXML
	private TextField portField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private CheckBox settingsCheckbox;
	
	@FXML
	private Button launchButton;
	
	private LauncherEvent onRemember;
	
	public StageLauncherController()
	{
		onRemember = (protocol, host, port, username, password) -> {};
	}
	
	@Override
	public void setOnRemember(LauncherEvent launcherEvent)
	{
		onRemember = launcherEvent;
	}
	
	@Override
	public void setOnLaunched(LauncherEvent launcherEvent)
	{
		launchButton.setOnAction(event -> launch(launcherEvent));
	}
	
	private void launch(LauncherEvent launcherEvent)
	{
		String protocol = protocolField.getSelectionModel().getSelectedItem();
		String host = hostField.getText();
		int port = Integer.parseInt(portField.getText());
		String username = usernameField.getText();
		String password = passwordField.getText();
		boolean remember = settingsCheckbox.isSelected();
		
		if (remember)
		{
			onRemember.call(protocol, host, port, username, password);
		}
		
		launcherEvent.call(protocol, host, port, username, password);
	}
	
	@Override
	public void setAvailableProtocols(String... protocols)
	{
		protocolField.getItems().setAll(protocols);
	}
	
	@Override
	public void setProtocol(String protocol)
	{
		protocolField.getSelectionModel().select(protocol);
	}
	
	@Override
	public void setHost(String host)
	{
		hostField.setText(host);
	}
	
	@Override
	public void setPort(int port)
	{
		portField.setText(String.valueOf(port));
	}
	
	@Override
	public void setUsername(String username)
	{
		usernameField.setText(username);
	}
	
	@Override
	public void setPassword(String password)
	{
		passwordField.setText(password);
	}
}