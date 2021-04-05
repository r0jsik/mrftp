package mr.walk;

public interface Walk
{
	void to(String entry);
	String resolve(String path);
	void out();
	void home();
}