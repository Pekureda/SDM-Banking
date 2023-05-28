package Bank.Reporting;

import Bank.Account;

public class OverNAccountsReport implements AccountVisitor {
    public final double N;
    public OverNAccountsReport(double N) {
        this.N = N;
    }
    @Override
    public Account visit(Account account) {
        if (account.getBalance() > N) return account;
        return null;
    }
}
