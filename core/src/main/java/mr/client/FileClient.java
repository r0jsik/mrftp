package mr.client;

import mr.entry.EntriesProjector;
import mr.entry.FileEntriesProjector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.function.BiConsumer;

public class FileClient implements Client
{
	@Override
	public void write(String path, StreamProvider<OutputStream> callback)
	{
		createParentDirectories(path);
		
		try (OutputStream outputStream = new FileOutputStream(path))
		{
			callback.provide(outputStream);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void createParentDirectories(String path)
	{
		File file = new File(path);
		File fileParent = file.getParentFile();
		fileParent.mkdirs();
	}
	
	@Override
	public void read(String path, StreamProvider<InputStream> callback)
	{
		File file = new File(path);
		
		try (InputStream inputStream = new FileInputStream(file))
		{
			callback.provide(inputStream);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void remove(String path)
	{
		File file = new File(path);
		
		try
		{
			Files.walk(file.toPath()).sorted(Comparator.reverseOrder()).forEach(this::remove);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void remove(Path path)
	{
		try
		{
			Files.delete(path);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	@Override
	public void walk(String from, String entry, BiConsumer<String, Boolean> callback)
	{
		try
		{
			tryToWalk(from, entry, callback);
		}
		catch (IOException exception)
		{
			throw new ClientActionException(exception);
		}
	}
	
	private void tryToWalk(String from, String entry, BiConsumer<String, Boolean> callback) throws IOException
	{
		Path path = Paths.get(from, entry);
		int elementsToSkip = path.getNameCount() - 1;
		
		Files.walk(path).filter(Files::isRegularFile).forEach(resolvedPath -> {
			int elements = resolvedPath.getNameCount();
			String string = resolvedPath.subpath(elementsToSkip, elements).toString();
			string = string.replaceAll("\\\\", "/");
			
			callback.accept(string, false);
		});
	}
	
	@Override
	public void close()
	{
	
	}
	
	@Override
	public EntriesProjector entriesProjector()
	{
		return new FileEntriesProjector();
	}
}