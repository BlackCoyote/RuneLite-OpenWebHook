package OpenWebHook.Events;

public class ChatSentEvent extends WebHookEvent {
    /**
     * The chat message that was sent.
     */
    public String chatMessage;

    public ChatSentEvent(String message) {
        chatMessage = message;
        eventType = WebHookEventType.ChatSent;
    }

}
