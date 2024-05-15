package OpenWebHook.Events;

import OpenWebHook.OpenWebHookPlugin;
import net.runelite.api.ItemComposition;
import net.runelite.api.NPC;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.loottracker.LootReceived;
import net.runelite.client.util.QuantityFormatter;
import net.runelite.http.api.loottracker.LootRecordType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class LootReceivedEvent extends WebHookEvent {

    public int itemID;

    public String itemName;

    public int itemQuantity;

    public int ItemBaseValue;

    public LootRecordType lootType;

    public String source;

    public LootReceivedEvent(int id, String name, int quantity, int value, LootRecordType type, String sc) {
        itemID = id;
        itemName = name;
        itemQuantity = quantity;
        ItemBaseValue = value;
        lootType = type;
        source = sc;
        eventType = WebHookEventType.LootReceived;
    }
}
