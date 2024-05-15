package OpenWebHook.Events;


import net.runelite.api.GameState;

public class GameStateChangedEvent extends WebHookEvent {
    /**
     * The new GameState of the player.
     */
    public GameState gameState;

    public GameStateChangedEvent(GameState state) {
        gameState = state;
        eventType = WebHookEventType.GameStateChanged;
    }

}
