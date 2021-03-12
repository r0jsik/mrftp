package mr.init;

import mr.scene.FxmlSceneFactory;
import mr.scene.SceneFactory;
import mr.scene.theme.JMetroTheme;
import mr.scene.theme.Theme;
import mr.warning.AlertWarning;
import mr.warning.Warning;
import mr.warning.WarningAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class StageConfiguration
{
	@Bean
	public SceneFactory sceneFactory()
	{
		return new FxmlSceneFactory("layout.fxml", "labels");
	}
	
	@Bean
	public Theme theme()
	{
		return new JMetroTheme();
	}
	
	@Bean
	public WarningAspect warningAspect(Warning warning)
	{
		return new WarningAspect(warning);
	}
	
	@Bean
	public Warning warning()
	{
		return new AlertWarning();
	}
}