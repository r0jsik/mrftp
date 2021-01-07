package mr.main;

import javafx.application.Application;
import javafx.stage.Stage;
import mr.stage.SimpleStageInitializer;
import mr.stage.StageInitializer;

public class JavaFX extends Application
{
	private final StageInitializer stageInitializer;
	
	public static void main(String[] args)
	{
		Application.launch(JavaFX.class, args);
	}
	
	public JavaFX()
	{
		this.stageInitializer = new SimpleStageInitializer("MrFTP");
	}
	
	@Override
	public void start(Stage stage)
	{
		stageInitializer.initialize(stage);
		stage.show();
	}
}