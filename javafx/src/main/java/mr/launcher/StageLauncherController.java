package mr.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import mr.settings.Settings;

@RequiredArgsConstructor
public class StageLauncherController implements LauncherController
{
	@FXML
	private ComboBox<String> protocolChooser;
	
	@FXML
	private TextField hostnameField;
	
	@FXML
	private TextField portField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private CheckBox settingsCheckbox;
	
	private final LauncherService launcherService;
	private final Settings settings;
	
	@Override
	public void showAvailableProtocols(String... options)
	{
		protocolChooser.getItems().setAll(options);
	}
	
	@Override
	public void setProtocol(String protocol)
	{
		protocolChooser.getSelectionModel().select(protocol);
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
	
	@FXML
	private void launch()
	{
		String protocol = protocolChooser.getSelectionModel().getSelectedItem();
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
}