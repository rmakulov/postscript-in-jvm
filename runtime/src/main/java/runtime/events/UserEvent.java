package runtime.events;

/**
 * Created by user on 5/28/15.
 */
public class UserEvent extends Event {
    private final String command;

    public UserEvent(String command) {
        super(EventType.USER);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
