package Bank;

import Bank.OperationRecord;

public interface Operation {
    public abstract OperationRecord execute();
    public abstract String getOperationCode();


}
