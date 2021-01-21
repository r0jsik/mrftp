package mr.entry;

import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TableEntryView
{
	private ImageView icon;
	private String name;
	private String size;
}