package mr.init;

import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import mr.scene.theme.Theme;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThemeInitializer implements InitializingBean
{
	private final Scene launcherScene;
	private final Scene explorerScene;
	private final Theme theme;
	
	@Override
	public void afterPropertiesSet()
	{
		theme.stylizeDark(launcherScene);
		theme.stylizeDark(explorerScene);
	}
}