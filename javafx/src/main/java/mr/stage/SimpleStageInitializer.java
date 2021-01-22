package mr.stage;

import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleStageInitializer implements StageInitializer
{
	private final String title;
	
	@Override
	public void initialize(Stage stage)
	{
		stage.setTitle(title);
		
		stage.setOnCloseRequest(event -> {
			Platform.exit();
		});
	}
}