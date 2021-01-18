package mr.ftp.entry;

public interface Entry
{
	String getName();
	long getSize();
	boolean isDirectory();
}