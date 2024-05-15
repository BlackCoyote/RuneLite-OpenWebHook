package OpenWebHook.Events;

import OpenWebHook.Endpoints.Endpoint;
import OpenWebHook.OpenWebHookPlugin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.runelite.api.Client;
import net.runelite.api.World;
import net.runelite.api.WorldType;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class for all different WebHookEvent classes.
 */
public abstract class WebHookEvent {
    /**
     * The event type of this webhook message.
     */
    public WebHookEventType eventType = WebHookEventType.Unknown;
    /**
     * The username of the player for which this webhook message is being sent.
     */
    public String userName = null;
    /**
     * The account name of the player for which this webhook message is being sent.
     */
    public String accountName = null;
    /**
     * The X coordinate of the player's current location.
     */
    public int playerLocationX = 0;
    /**
     * The Y coordinate of the player's current location.
     */
    public int playerLocationY = 0;
    /**
     * The world that the player is in.
     */
    public int world = 0;
    /**
     * The world types that apply to this world
     */
    public List<WorldType> worldTypes = new ArrayList<>();
    /**
     * The activity of the world.
     */
    public String worldActivity = null;

    /**
     * Base constructor that automatically fills in common properties of all WebHookEvents.
     */
    public WebHookEvent() {
        Client c = OpenWebHookPlugin.instance.client;
        userName = c.getLocalPlayer().getName();
        accountName = c.getLauncherDisplayName() != null ? c.getLauncherDisplayName() : c.getUsername();
        playerLocationX = c.getBaseX();
        playerLocationY = c.getBaseY();
        World currentWorld = OpenWebHookPlugin.instance.currentWorld;
        if (currentWorld != null) {
            world = currentWorld.getId();
            worldTypes.addAll(c.getWorldType());
            worldActivity = currentWorld.getActivity();
        }
    }

    /**
     * Sends the WebHookMessage to the configured endpoints.
     */
    public void Send() {
        Endpoint.SendEvent(this);
    }

    /**
     * Serializes this WebHookEvent to JSON.
     * @return The JSON based serialized version of this WebHookEvent.
     */
    public JsonElement ToJson() {
        return OpenWebHookPlugin.jsonConverter.toJsonTree(this);
    }

}
