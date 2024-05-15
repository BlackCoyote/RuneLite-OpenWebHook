package OpenWebHook.Events;

import OpenWebHook.Endpoints.Endpoint;
import OpenWebHook.OpenWebHookPlugin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.runelite.api.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base class for all different WebHookEvent classes.
 */
public abstract class WebHookEvent {
    /**
     * The JSON converter used to serialize the webhook events for http delivery.
     */
    private static final Gson jsonConverter = new GsonBuilder().serializeNulls().create();
    /**
     * The event type of this webhook message.
     */
    public WebHookEventType eventType;
    /**
     * The username of the player for which this webhook message is being sent.
     */
    public String userName = null;
    /**
     * The X coordinate of the player's current location.
     */
    public int playerLocationX = 0;
    /**
     * The Y coordinate of the player's current location.
     */
    public int playerLocationY = 0;

    /**
     * Base constructor that automatically fills in common properties of all WebHookMessages.
     */
    public WebHookEvent() {
        Client c = OpenWebHookPlugin.instance.client;
        userName = c.getLocalPlayer().getName();
        if (userName == null) {
            userName = c.getLauncherDisplayName() != null ? c.getLauncherDisplayName() : c.getUsername();
        }
        playerLocationX = c.getBaseX();
        playerLocationY = c.getBaseY();
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
    public String ToJson() {
        return jsonConverter.toJson(this);
    }

}
