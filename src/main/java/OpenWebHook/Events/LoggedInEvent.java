package OpenWebHook.Events;


public class LoggedInEvent extends WebHookEvent {

    public LoggedInEvent() {
        eventType = WebHookEventType.LoggedIn;
    }

}
