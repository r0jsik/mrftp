package mr.explorer;

import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import mr.ftp.entry.Entry;

@Setter
@Getter
public class EntryView
{
	private ImageView icon;
	private String name;
	private String size;
	
	public boolean refersTo(Entry entry)
	{
		return entry.getName().equals(name);
	}
}