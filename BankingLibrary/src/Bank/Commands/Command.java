package Bank.Commands;

import Bank.VisitorReceiver;

public interface Command extends VisitorReceiver {
    public boolean execute();
}
