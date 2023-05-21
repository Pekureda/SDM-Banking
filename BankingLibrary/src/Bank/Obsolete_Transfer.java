package Bank;

public class Obsolete_Transfer implements Obsolete_Operation {
    private Account source, destination;
    private long amount;
    public Obsolete_Transfer(Account source, Account destination, long amount){
        this.source=source;
        this.destination=destination;
        if (amount < 0)
            throw new IllegalArgumentException("Transfer amount can not be negative");
        this.amount = amount;
    }

    @Override
    public Obsolete_OperationRecord execute(){
        String desc = String.format("Transfer %d,from %d to $d", amount, source.getId(), destination.getId());
        try {
            source.updateBalance(-amount);
        }
        catch (Exception e){
            return new Obsolete_OperationRecord(Obsolete_OperationRecord.Status.FAIL, desc, this);
        }
        try {
            destination.updateBalance(amount);
        }
        catch (Exception e) {
            try {
                source.updateBalance(amount);
            }
            catch (Exception e2){}
            return new Obsolete_OperationRecord(Obsolete_OperationRecord.Status.FAIL, desc, this);
        }
        return new Obsolete_OperationRecord(Obsolete_OperationRecord.Status.SUCCESS, desc, this);
    }

    @Override
    public String getOperationCode() {
        return "TRANSFER";
    }
}
