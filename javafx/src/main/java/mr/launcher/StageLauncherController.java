package mr.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import lombok.RequiredArgsConstructor;
import mr.settings.Settings;

@RequiredArgsConstructor
public class StageLauncherController implements LauncherController
{
	@FXML
	private ComboBox<String> protocolField;
	
	@FXML
	private TextField hostnameField;
	
	@FXML
	private TextField portField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private CheckBox settingsCheckbox;
	
	@FXML
	private Label protocolLabel;
	
	@FXML
	private Label hostnameLabel;
	
	@FXML
	private Label portLabel;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Label passwordLabel;
	
	@FXML
	private Button launchButton;
	
	private final LauncherService launcherService;
	private final Settings settings;
	
	@FXML
	private void initialize()
	{
		launchButton.setOnAction(event -> launch());
	}
	
	private void launch()
	{
		String protocol = protocolField.getSelectionModel().getSelectedItem();
		String hostname = hostnameField.getText();
		int port = Integer.parseInt(portField.getText());
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		if (settingsCheckbox.isSelected())
		{
			settings.setHostname(hostname);
			settings.setPort(port);
			settings.setUsername(username);
			settings.setPassword(password);
			settings.commit();
		}
		
		launcherService.launch(protocol, hostname, port, username, password);
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
	public void setHostname(String hostname)
	{
		hostnameField.setText(hostname);
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
	
	@Override
	public void setProtocolLabel(String label)
	{
		protocolLabel.setText(label);
	}
	
	@Override
	public void setHostnameLabel(String label)
	{
		hostnameLabel.setText(label);
	}
	
	@Override
	public void setPortLabel(String label)
	{
		portLabel.setText(label);
	}
	
	@Override
	public void setUsernameLabel(String label)
	{
		usernameLabel.setText(label);
	}
	
	@Override
	public void setPasswordLabel(String label)
	{
		passwordLabel.setText(label);
	}
	
	@Override
	public void setSettingsLabel(String label)
	{
		settingsCheckbox.setText(label);
	}
	
	@Override
	public void setStartLabel(String label)
	{
		launchButton.setText(label);
	}
}