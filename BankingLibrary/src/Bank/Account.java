package Bank;

import java.time.LocalDate;

class Account {
    private static long idCounter=0;
    private long id;
    private Customer owner;
    private LocalDate opening;
    private long balance;
    private History history;
    public Account(Customer owner){
        id=idCounter++;
        this.owner = owner;
        opening = LocalDate.now();
        balance=0;
        history = new History();
    }

    public long getBalance() {
        return balance;
    }
    public void updateBalance(long delta) throws Exception{
        if (balance + delta < 0)
            throw new Exception("Update would result in negative balance");
        balance = balance+delta;
    }

    public long getId() {
        return id;
    }
}