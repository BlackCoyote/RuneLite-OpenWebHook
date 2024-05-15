package OpenWebHook.Events;

import net.runelite.api.ChatMessageType;

public class ChatSentEvent extends WebHookEvent {
    /**
     * The chat message that was sent.
     */
    public String chatMessage;
    /**
     * The message type that was sent.
     */
    public ChatMessageType chatMessageType;

    public ChatSentEvent(String message, ChatMessageType messageType) {
        chatMessage = message;
        chatMessageType = messageType;
        eventType = WebHookEventType.ChatSent;
    }

}
