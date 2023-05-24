package Bank;

import java.time.LocalDate;
import java.util.Currency;


public class Account implements InterestApplicableProduct {
    public double interestRate;

    public InterestRateStrategy myInterestRateStrategy;
    private static long idCounter=0;
    private String accountId;
    protected Customer owner;
    private LocalDate opening;
    protected double balance;
    private Currency currency;
    private History history;
    Account(Customer owner, double startingBalance, Currency currency) {
        this(owner);
        this.balance = startingBalance;
        this.currency = currency;
    }
    Account(Customer owner){
        accountId = String.format(owner.getBankRef().bankCode + "%010d", idCounter++);
        this.owner = owner;
        opening = LocalDate.now();
        balance = 0.0;
        history = new History();
    }

    public double getBalance() {
        return balance;
    }
    void updateBalance(double delta) throws Exception{
        if (balance + delta < 0.0)
            throw new Exception("Update would result in negative balance");
        balance = balance+delta;
    }

    public String getId() {
        return accountId;
    }

    Customer getOwner() {
        return owner;
    }

    boolean executeCommand(Command command) {
        if (command.execute()) {
            history.pushBack(command);
            owner.getBankRef().informCommandExecutionOnAccount(command);
            return true;
        }
        return false;
    }

    public boolean orderTransfer(double amount, Currency currency, String recipientAccountId, String text) {
        return owner.getBankRef().executeCommand(new OrderTransferCommand(owner.getBankRef(), this, amount, currency, recipientAccountId, text));
    }

    @Override
    public double getProductPrincipalAmount() {
        return balance;
    }

    @Override
    public double getProductRate() {
        return interestRate;

    }

    @Override
    public int getProductTime() {//czas calego okresu waloryzacji, to jeden okres kapitalizacji, takze
        //tutaj jedynka nie wazne czy robimy co miesiac czy co sekunde.
        return 1;
    }

    @Override
    public int getProductCompoundFrequency() {//tutaj tak samo jedynka zeby pasowalo do wzoru jakby ktos chcial uzyc startegii compound frequency

        return 1;
    }

    @Override
    public void changeInterestRate(double rate) {
        this.interestRate=rate;
    }

    @Override
    public Double calculateInterest() {
        return myInterestRateStrategy.calculateInterest(this);
    }

    @Override
    public boolean isDeposit() {
        return false;
    }

    @Override
    public boolean isLoan() {
        return false;
    }
}