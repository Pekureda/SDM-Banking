package Bank.Reporting;

import Bank.Account;

public interface AccountVisitor {
    Account visit(Account account);
}
