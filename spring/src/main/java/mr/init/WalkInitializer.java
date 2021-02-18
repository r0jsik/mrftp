package mr.init;

import lombok.RequiredArgsConstructor;
import mr.event.ClientChangedEvent;
import mr.walk.Walk;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalkInitializer implements ApplicationListener<ClientChangedEvent>
{
	private final Walk remoteWalk;
	private final Walk localWalk;
	
	@Override
	public void onApplicationEvent(ClientChangedEvent clientChangedEvent)
	{
		remoteWalk.home();
		localWalk.home();
	}
}