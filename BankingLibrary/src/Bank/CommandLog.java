package Bank;

import java.util.List;

public interface CommandLog {
    List<CommandLogEntry> logCommand();
}
