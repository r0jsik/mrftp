package mr.stage;

import javafx.scene.Scene;
import javafx.stage.Stage;

public interface StageInitializer
{
	void initializeLauncher(Stage stage, Scene scene);
	void initializeExplorer(Stage stage, Scene scene);
}