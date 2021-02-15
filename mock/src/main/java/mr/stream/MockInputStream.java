package mr.stream;

import java.io.ByteArrayInputStream;

public class MockInputStream extends ByteArrayInputStream
{
	public MockInputStream(String content)
	{
		super(content.getBytes());
	}
}