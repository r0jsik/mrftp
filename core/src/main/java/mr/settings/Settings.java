package mr.settings;

import java.util.function.Consumer;

public interface Settings
{
	void update(Consumer<SettingsContext> callback);
	void select(Consumer<SettingsContext> callback);
}