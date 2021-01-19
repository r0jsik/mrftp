package mr.scene.theme;

import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StylesheetTheme implements Theme
{
	private static final String darkStylesheet = StylesheetTheme.class.getResource("/dark.css").toString();
	
	private final boolean isDark;
	
	@Override
	public void stylize(Scene scene)
	{
		if (isDark)
		{
			scene.getStylesheets().add(darkStylesheet);
		}
	}
}