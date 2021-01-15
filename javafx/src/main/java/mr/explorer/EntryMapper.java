package mr.explorer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import mr.ftp.entry.Entry;

public class EntryMapper
{
	public static ObservableValue map(Entry entry, int index)
	{
		return new SimpleStringProperty(getMapping(entry, index));
	}
	
	private static String getMapping(Entry entry, int index)
	{
		switch (index)
		{
			case 0:
				return entry.getName();
			case 1:
				return "";
			default:
				throw new IndexOutOfBoundsException();
		}
	}
}