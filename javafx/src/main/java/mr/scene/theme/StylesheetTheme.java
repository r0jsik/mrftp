package mr.scene.theme;

import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StylesheetTheme implements Theme
{
	private static final String darkStylesheet = StylesheetTheme.class.getResource("/dark.css").toString();
	
	@Override
	public void stylizeDark(Scene scene)
	{
		scene.getStylesheets().add(darkStylesheet);
	}
}