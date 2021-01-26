package mr.mock;

import java.io.ByteArrayInputStream;

public class MockInputStream extends ByteArrayInputStream
{
	public MockInputStream()
	{
		super("Mock content".getBytes());
	}
}