package OpenWebHook.WebHookMessages;

public class LoggedOutMessage extends WebHookMessage {

    public LoggedOutMessage() {
        messageType = WebHookMessageType.LoggedOut;
    }

}
