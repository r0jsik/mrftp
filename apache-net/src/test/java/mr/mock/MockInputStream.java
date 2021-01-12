package mr.mock;

import java.io.ByteArrayInputStream;

public class MockInputStream extends ByteArrayInputStream
{
	public MockInputStream(String data)
	{
		super(data.getBytes());
	}
}