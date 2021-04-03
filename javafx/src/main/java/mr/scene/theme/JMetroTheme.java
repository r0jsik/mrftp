package mr.scene.theme;

import javafx.scene.Scene;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class JMetroTheme implements Theme
{
	@Override
	public void stylizeDark(Scene scene)
	{
		stylize(scene, Style.DARK);
	}
	
	private void stylize(Scene scene, Style style)
	{
		JMetro jmetro = new JMetro(style);
		jmetro.setAutomaticallyColorPanes(true);
		jmetro.setScene(scene);
	}
	
	@Override
	public void stylizeLight(Scene scene)
	{
		stylize(scene, Style.LIGHT);
	}
}