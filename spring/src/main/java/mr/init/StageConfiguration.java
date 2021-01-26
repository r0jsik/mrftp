package mr.init;

import mr.scene.FxmlSceneFactory;
import mr.scene.SceneFactory;
import mr.scene.theme.StylesheetTheme;
import mr.scene.theme.Theme;
import mr.stage.SimpleStageInitializer;
import mr.stage.StageInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StageConfiguration
{
	@Bean
	public StageInitializer stageInitializer()
	{
		return new SimpleStageInitializer("MrFTP");
	}
	
	@Bean
	public SceneFactory sceneFactory()
	{
		return new FxmlSceneFactory("layout.fxml");
	}
	
	@Bean
	public Theme theme()
	{
		return new StylesheetTheme();
	}
}