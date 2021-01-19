package mr.scene.theme;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

public class StylesheetThemeTest
{
	@Test
	public void testStylizeDark()
	{
		Pane root = new Pane();
		Scene scene = new Scene(root);
		
		Theme theme = new StylesheetTheme(true);
		theme.stylize(scene);
	}
	
	@Test
	public void testStylizeDefault()
	{
		Pane root = new Pane();
		Scene scene = new Scene(root);
		
		Theme theme = new StylesheetTheme(false);
		theme.stylize(scene);
	}
}