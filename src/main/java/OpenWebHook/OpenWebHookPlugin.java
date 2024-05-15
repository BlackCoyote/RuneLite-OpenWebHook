package OpenWebHook;

import OpenWebHook.Endpoints.Endpoint;
import OpenWebHook.Events.ChatSentEvent;
import OpenWebHook.Events.GameStateChangedEvent;
import OpenWebHook.Events.LootReceivedEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WorldChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.loottracker.LootReceived;
import net.runelite.client.util.Text;
import net.runelite.http.api.loottracker.LootRecordType;

import java.util.Collection;
import java.util.List;

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

	@Inject
	public ItemManager itemManager;
	/**
	 * The World that the player is currently in, if any.
	 */
	public World currentWorld = null;
	/**
	 * The JSON converter used to serialize the contents being sent over the webhooks.
	 */
	public static final Gson jsonConverter = new GsonBuilder().serializeNulls().create();

	/**
	 * plugin and game state handling
	 */
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
		GameState state = gameStateChanged.getGameState();
		if (state == GameState.LOADING) {
			return;
		}
		new GameStateChangedEvent(state).Send();
	}

	@Subscribe
	public void onWorldChanged(WorldChanged worldChanged) {
		updateWorld();
	}

	public void updateWorld() {
		World[] worlds = client.getWorldList();
		int currentWorldID = client.getWorld();
		for (World w : client.getWorldList()) {
			if (currentWorldID == w.getId()) {
				currentWorld = w;
				return;
			}
		}
		currentWorld = null;
	}

	/**
	 * Chat handling
	 */
	@Subscribe
	public void onChatMessage(ChatMessage message) {
		String sender = Text.standardize(message.getName());
		String player = Text.standardize(client.getLocalPlayer().getName());
		if (sender != null && sender.equalsIgnoreCase(player)) {
			new ChatSentEvent(message.getMessage(), message.getType()).Send();
		}
	}

	/**
	 * Loot handling
	 */
	@Subscribe
	public void onNpcLootReceived(NpcLootReceived npcLootReceived)
	{
		NPC npc = npcLootReceived.getNpc();
		Collection<ItemStack> items = npcLootReceived.getItems();
		ProcessLoot(items, LootRecordType.NPC, Text.standardize(npcLootReceived.getNpc().getName()));
	}

	@Subscribe
	public void onPlayerLootReceived(PlayerLootReceived playerLootReceived)
	{
		Collection<ItemStack> items = playerLootReceived.getItems();
		ProcessLoot(items, LootRecordType.PLAYER, Text.standardize(playerLootReceived.getPlayer().getName()));
	}

	public void ProcessLoot(Collection<ItemStack> items, LootRecordType type, String source) {
		for (ItemStack item : items)
		{
			int itemId = item.getId();
			ItemComposition itemComposition = OpenWebHookPlugin.instance.itemManager.getItemComposition(itemId);

			String itemName = itemComposition.getName();
			int qty = item.getQuantity();
			int value = OpenWebHookPlugin.instance.itemManager.getItemPrice(itemId);
			new LootReceivedEvent(itemId, itemName, qty, value, type, source).Send();
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
