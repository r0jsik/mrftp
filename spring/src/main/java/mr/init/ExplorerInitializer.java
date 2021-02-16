package mr.init;

import lombok.RequiredArgsConstructor;
import mr.explorer.ExplorerController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("explorerScene")
@RequiredArgsConstructor
public class ExplorerInitializer implements InitializingBean
{
	private final ExplorerController explorerController;
	
	@Override
	public void afterPropertiesSet()
	{
		initializeLabels();
	}
	
	private void initializeLabels()
	{
		explorerController.setCloseLabel("Zamknij");
		explorerController.setRefreshLabel("Odśwież");
	}
}