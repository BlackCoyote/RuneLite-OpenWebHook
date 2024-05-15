package OpenWebHook;

import OpenWebHook.Endpoints.Endpoint;
import OpenWebHook.Events.LoggedInEvent;
import OpenWebHook.Events.LoggedOutEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
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

	@Inject
	private OpenWebHookConfig config;

	/**
	 * The JSON converter used to serialize the contents being sent over the webhooks.
	 */
	public static final Gson jsonConverter = new GsonBuilder().serializeNulls().create();

	@Override
	protected void startUp() throws Exception
	{
		instance = this;
		ConfigureEndpoints();
	}

	@Override
	protected void shutDown() throws Exception
	{
		new LoggedOutEvent().Send();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			new LoggedInEvent().Send();
		}
		else if (gameStateChanged.getGameState() == GameState.CONNECTION_LOST) {
			new LoggedOutEvent().Send();
		}
	}

	@Provides
	OpenWebHookConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(OpenWebHookConfig.class);
	}

	public void ConfigureEndpoints() {
		Endpoint.BuildFromURLs(new String[] { "http://peebot.blackcoyote.net:9000" }); // TODO read from plugin configuration
	}
}
