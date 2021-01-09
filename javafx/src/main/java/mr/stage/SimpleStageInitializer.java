package mr.stage;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleStageInitializer implements StageInitializer
{
	private final String title;
	private final Scene scene;
	
	@Override
	public void initialize(Stage stage)
	{
		stage.setTitle(title);
		stage.setScene(scene);
		
		stage.setOnCloseRequest(event -> {
			Platform.exit();
		});
	}
}