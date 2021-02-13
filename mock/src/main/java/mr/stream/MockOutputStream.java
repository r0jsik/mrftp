package mr.stream;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MockOutputStream extends ByteArrayOutputStream
{
	public boolean hasContent(String content)
	{
		return Arrays.equals(super.toByteArray(), content.getBytes());
	}
}