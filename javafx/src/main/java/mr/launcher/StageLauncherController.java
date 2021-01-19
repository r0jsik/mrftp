package mr.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

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
	
	private final LauncherService launcherService;
	
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
		portField.setText(username);
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
		
		launcherService.launch(hostname, port, username, password);
	}
}