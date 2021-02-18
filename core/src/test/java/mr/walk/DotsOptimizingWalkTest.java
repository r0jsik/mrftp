package mr.walk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class DotsOptimizingWalkTest
{
	private final List<String> initialPath;
	
	public DotsOptimizingWalkTest()
	{
		initialPath = Arrays.asList(".", "directory-name", "other-dir");
	}
	
	@Test
	public void testWalkTo()
	{
		Iterable<CharSequence> expectedPath = Arrays.asList(".", "directory-name", "other-dir", "last-dir");
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		walk.to("last-dir");
		
		Assertions.assertIterableEquals(expectedPath, path);
	}
	
	@Test
	public void testWalkOut()
	{
		Iterable<CharSequence> expectedPath = Arrays.asList(".", "directory-name");
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		walk.to("..");
		
		Assertions.assertIterableEquals(expectedPath, path);
	}
	
	@Test
	public void testWalkToAndThenOut()
	{
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		walk.to("last-dir");
		walk.to("..");
		
		Assertions.assertIterableEquals(initialPath, path);
	}
	
	@Test
	public void testWalkOutAndThenTo()
	{
		Iterable<CharSequence> expectedPath = Arrays.asList(".", "directory-name", "last-dir");
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		walk.to("..");
		walk.to("last-dir");
		
		Assertions.assertIterableEquals(expectedPath, path);
	}
	
	@Test
	public void testWalkOutToNowhere()
	{
		Walk walk = new DequeWalk();
		
		Assertions.assertDoesNotThrow(() -> {
			walk.to("..");
			walk.to("..");
			walk.to("..");
		});
	}
	
	@Test
	public void testToString()
	{
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		
		String expectedPath = "./directory-name/other-dir";
		String actualPath = walk.toString();
		
		Assertions.assertEquals(expectedPath, actualPath);
	}
	
	@Test
	public void testResolve()
	{
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		
		String expectedPath = "./directory-name/other-dir/last-dir";
		String resolvedPath = walk.resolve("last-dir");
		
		Assertions.assertEquals(expectedPath, resolvedPath);
	}
	
	@Test
	public void testResolveDoesNotChangeWalk()
	{
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		walk.resolve("last-dir");
		
		Assertions.assertIterableEquals(initialPath, path);
	}
	
	@Test
	public void testHome()
	{
		Iterable<CharSequence> expectedPath = Collections.singletonList(".");
		Deque<CharSequence> path = new ArrayDeque<>(initialPath);
		Walk walk = new DequeWalk(path);
		walk = new DotsOptimizingWalk(walk);
		walk.home();
		
		Assertions.assertIterableEquals(expectedPath, path);
	}
}