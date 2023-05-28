package Bank.Reporting;

import Bank.Commands.Command;
import Bank.History;

import java.util.List;

public interface HistoryVisitor {
    List<Command> visit(History history);
}
