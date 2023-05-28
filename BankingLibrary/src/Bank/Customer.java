package Bank;

import Bank.Commands.Command;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

public class Customer implements VisitorReceiver {
    private String username;
    private String name;
    private String surname;
    Customer(String name, String surname, String username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }

    @Override
    public Account accept(AccountVisitor visitor) {
        return null;
    }

    @Override
    public Command accept(TransactionVisitor visitor) {
        return null;
    }

    @Override
    public Customer accept(CustomerVisitor visitor) {
        return visitor.visit(this);
    }
}
