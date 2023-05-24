package Bank;

import java.time.LocalDateTime;

public interface Command {
    public boolean execute();
    public LocalDateTime getExecutionTime();
}
