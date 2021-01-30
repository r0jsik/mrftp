package mr.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import mr.settings.Settings;

@RequiredArgsConstructor
public class StageLauncherController implements LauncherController
{
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
	
	@FXML
	private void initialize()
	{
		setHostname(settings.getHostname());
		setPort(settings.getPort());
		setUsername(settings.getUsername());
		setPassword(settings.getPassword());
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
		
		launcherService.launch(hostname, port, username, password);
	}
}