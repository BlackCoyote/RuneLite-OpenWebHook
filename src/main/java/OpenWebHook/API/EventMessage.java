package OpenWebHook.API;

import OpenWebHook.Events.WebHookEvent;
import com.google.gson.JsonObject;

public class EventMessage {
    /**
     * The WebHookEvent for which this API EventMessage is being generated.
     */
    public WebHookEvent messageEvent;

    /**
     * Creates a new EventMessage for a WebHookEvent.
     * @param ev The WebHookEvent for which to create a n eventMessage.
     */
    public EventMessage(WebHookEvent ev) {
        messageEvent = ev;
    }

    /**
     * Generates the complete JSON contents for this API message.
     * @return The JSON contents for this API message.
     */
    public String AsPayload() {
        JsonObject message = new JsonObject();
        message.addProperty(CommonProperties.PayloadType.toString(), PayloadTypeProperty.EventMessage.toString());
        message.add(CommonProperties.Payload.toString(), messageEvent.ToJson());
        return message.toString();
    }
}
