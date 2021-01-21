package mr.walk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class DequeWalkTest
{
	private final List<String> initialPath;
	
	public DequeWalkTest()
	{
		initialPath = Arrays.asList(".", "directory-name", "other-dir");
	}
	
	@Test
	public void testWalkToDirectory()
	{
		List<CharSequence> expectedPath = Arrays.asList(".", "directory-name", "other-dir", "last-dir");
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		
		Walk walk = new DequeWalk(path);
		walk.to("last-dir");
		
		Assertions.assertIterableEquals(expectedPath, path);
	}
	
	@Test
	public void testWalkOut()
	{
		List<CharSequence> expectedPath = Arrays.asList(".", "directory-name");
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		
		Walk walk = new DequeWalk(path);
		walk.to("..");
		
		Assertions.assertIterableEquals(expectedPath, path);
	}
	
	@Test
	public void testToString()
	{
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		Walk walk = new DequeWalk(path);
		
		String expectedPath = "./directory-name/other-dir";
		String actualPath = walk.toString();
		
		Assertions.assertEquals(expectedPath, actualPath);
	}
}