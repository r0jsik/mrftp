package mr.walk;

public interface Walk
{
	void to(String entry);
	String resolve(String entry);
	void out();
}