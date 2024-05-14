package OpenWebHook;

import OpenWebHook.WebHookMessages.LoggedInMessage;
import OpenWebHook.WebHookMessages.LoggedOutMessage;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class OpenWebHookPlugin extends Plugin
{
	@Inject
	public Client client;

	public static OpenWebHookPlugin instance;

	public WebHookSender webHookSender = new WebHookSender();

	@Inject
	private OpenWebHookConfig config;

	@Override
	protected void startUp() throws Exception
	{
		instance = this;
	}

	@Override
	protected void shutDown() throws Exception
	{
		new LoggedOutMessage().Send();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			new LoggedInMessage().Send();
		}
		else if (gameStateChanged.getGameState() == GameState.CONNECTION_LOST) {
			new LoggedOutMessage().Send();
		}
	}

	@Provides
	OpenWebHookConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(OpenWebHookConfig.class);
	}
}
