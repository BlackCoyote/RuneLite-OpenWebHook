package OpenWebHook;

import OpenWebHook.Endpoints.Endpoint;
import OpenWebHook.Events.ChatSentEvent;
import OpenWebHook.Events.GameStateChangedEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "OpenWebHook"
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
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		new GameStateChangedEvent(gameStateChanged.getGameState()).Send();
	}

	@Subscribe
	public void onChatMessage(ChatMessage message) {
		String sender = Text.standardize(message.getName());
		String player = Text.standardize(client.getLocalPlayer().getName());
		if (sender != null && sender.equalsIgnoreCase(player)) {
			new ChatSentEvent(message.getMessage()).Send();
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
