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
		stage.sizeToScene();
		stage.setResizable(false);
		
		initialize(stage, scene);
	}
	
	private void initialize(Stage stage, Scene scene)
	{
		stage.setTitle(title);
		stage.setScene(scene);
		
		stage.setOnCloseRequest(event -> {
			Platform.exit();
		});
	}
	
	@Override
	public void initializeExplorer(Stage stage, Scene scene)
	{
		stage.setMinWidth(640);
		stage.setMinHeight(480);
		stage.setWidth(640);
		stage.setHeight(480);
		
		initialize(stage, scene);
	}
}