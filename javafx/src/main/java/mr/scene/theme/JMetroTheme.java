package mr.scene.theme;

import javafx.scene.Scene;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class JMetroTheme implements Theme
{
	@Override
	public void stylizeDark(Scene scene)
	{
		JMetro jmetro = new JMetro(Style.DARK);
		jmetro.setAutomaticallyColorPanes(true);
		jmetro.setScene(scene);
	}
}