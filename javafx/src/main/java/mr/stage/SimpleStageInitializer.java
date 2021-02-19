package mr.stage;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleStageInitializer implements StageInitializer
{
	private final String title;
	
	@Override
	public void initializeLauncher(Stage stage, Scene scene)
	{
		initialize(stage, scene);
	}
	
	private void initialize(Stage stage, Scene scene)
	{
		stage.setTitle(title);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setResizable(false);
		
		stage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
	}
	
	@Override
	public void initializeExplorer(Stage stage, Scene scene)
	{
		initialize(stage, scene);
	}
}