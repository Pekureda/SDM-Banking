package Bank;

import Bank.Operation;
import Bank.OperationRecord;

public class Transfer implements Operation {
    private Account source, destination;
    private long amount;
    public Transfer(Account source, Account destination, long amount){
        this.source=source;
        this.destination=destination;
        if (amount < 0)
            throw new IllegalArgumentException("Transfer amount can not be negative");
        this.amount = amount;
    }

    @Override
    public OperationRecord execute(){
        String desc = String.format("Transfer %d,from %d to $d", amount, source.getId(), destination.getId());
        try {
            source.updateBalance(-amount);
        }
        catch (Exception e){
            return new OperationRecord(OperationRecord.Status.FAIL, desc, this);
        }
        try {
            destination.updateBalance(amount);
        }
        catch (Exception e) {
            try {
                source.updateBalance(amount);
            }
            catch (Exception e2){}
            return new OperationRecord(OperationRecord.Status.FAIL, desc, this);
        }
        return new OperationRecord(OperationRecord.Status.SUCCESS, desc, this);
    }

    @Override
    public String getOperationCode() {
        return "TRANSFER";
    }
}
