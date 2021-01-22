package mr.init;

import mr.scene.FxmlSceneFactory;
import mr.scene.SceneFactory;
import mr.scene.theme.StylesheetTheme;
import mr.scene.theme.Theme;
import mr.scene.theme.ThemeSceneFactory;
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
	public SceneFactory sceneFactory(Theme theme)
	{
		SceneFactory sceneFactory = new FxmlSceneFactory("layout.fxml");
		sceneFactory = new ThemeSceneFactory(sceneFactory, theme);
		
		return sceneFactory;
	}
	
	@Bean
	public Theme theme()
	{
		return new StylesheetTheme(true);
	}
}