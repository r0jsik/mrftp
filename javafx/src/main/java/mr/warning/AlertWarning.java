package mr.warning;

import javafx.scene.control.Alert;

public class AlertWarning implements Warning
{
	@Override
	public void show(String message, Exception exception)
	{
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Wystąpił problem");
		alert.setHeaderText(message);
		alert.setContentText(exception.getMessage());
		alert.setResizable(false);
		alert.showAndWait();
	}
}