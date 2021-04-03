package mr.init;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import mr.event.StartExplorerEvent;
import mr.event.StartLauncherEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageLogic
{
	private final Scene launcherScene;
	private final Scene explorerScene;
	
	private Stage stage;
	
	@EventListener(StartLauncherEvent.class)
	public void showLauncher()
	{
		show(launcherScene);
	}
	
	private void show(Scene scene)
	{
		stage.close();
		stage = new Stage();
		
		stage.setTitle("MrFTP");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setResizable(false);
		
		stage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
		
		stage.show();
	}
	
	@EventListener(StartExplorerEvent.class)
	public void onApplicationEvent()
	{
		show(explorerScene);
	}
	
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
}