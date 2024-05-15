package OpenWebHook.Events;

public class LoggedOutEvent extends WebHookEvent {

    public LoggedOutEvent() {
        eventType = WebHookEventType.LoggedOut;
    }

}
