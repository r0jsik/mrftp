package mr.warning;

import lombok.RequiredArgsConstructor;
import mr.client.ClientActionException;
import mr.client.ClientFactoryException;
import mr.entry.EntriesProjectionException;
import mr.scene.SceneFactoryException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class WarningAspect
{
	private final Warning warning;
	
	@AfterThrowing(pointcut = "execution(* mr.scene.SceneFactory.create(..))", throwing = "exception")
	private void unableToCreateScene(SceneFactoryException exception)
	{
		warning.show("Nie można załadować sceny", exception);
	}
	
	@AfterThrowing(pointcut = "execution(* mr.client.ClientFactory.create(..))", throwing = "exception")
	private void unableToCreateClient(ClientFactoryException exception)
	{
		warning.show("Nie można nawiązać połączenia", exception);
	}
	
	@AfterThrowing(pointcut = "execution(* mr.init.RemoteEntriesViewLogic.onApplicationEvent(..))", throwing = "exception")
	private void unableToShowRemoteEntries(EntriesProjectionException exception)
	{
		warning.show("Nie można pokazać zawartości katalogu", exception);
	}
	
	@AfterThrowing(pointcut = "execution(* mr.init.LocalEntriesViewLogic.onApplicationEvent(..))", throwing = "exception")
	private void unableToShowLocalEntries(EntriesProjectionException exception)
	{
		warning.show("Nie można pokazać zawartości katalogu", exception);
	}
	
	@AfterThrowing(pointcut = "execution(* mr.client.Client.write(..))", throwing = "exception")
	private void unableToUploadEntry(ClientActionException exception)
	{
		warning.show("Nie można zapisać danych", exception);
	}
	
	@AfterThrowing(pointcut = "execution(* mr.client.Client.read(..))", throwing = "exception")
	private void unableToDownloadEntry(ClientActionException exception)
	{
		warning.show("Nie można odczytać danych", exception);
	}
	
	@AfterThrowing(pointcut = "execution(* mr.client.Client.remove(..))", throwing = "exception")
	private void unableToRemoveEntry(ClientActionException exception)
	{
		warning.show("Nie można usunąć danych", exception);
	}
}