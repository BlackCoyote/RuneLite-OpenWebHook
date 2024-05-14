package OpenWebHook.WebHookMessages;

import OpenWebHook.OpenWebHookPlugin;
import com.google.gson.Gson;
import net.runelite.api.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base class for all different WebHookMessage classes.
 */
public abstract class WebHookMessage {
    /**
     * The message type of this webhook message.
     */
    public WebHookMessageType messageType;
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
    public WebHookMessage() {
        Client c = OpenWebHookPlugin.instance.client;
        userName = c.getLocalPlayer().getName();
        playerLocationX = c.getBaseX();
        playerLocationY = c.getBaseY();
    }

    /**
     * Sends the WebHookMessage to the configured endpoints.
     */
    public void Send() {
        Gson gson = new Gson();
        String json = gson.toJson();
        OpenWebHookPlugin.instance.webHookSender.Send(json);
    }

}
