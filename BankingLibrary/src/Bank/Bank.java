package Bank;

import Bank.Commands.*;
import Bank.InterestRate.AccountInterestRateStrategy;
import Bank.InterestRate.DepositInterestRateStrategy;
import Bank.InterestRate.InterestRateStrategy;
import Bank.InterestRate.LoanInterestRateStrategy;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.HistoryVisitor;

import java.util.*;

public class Bank implements OperationExecutor, VisitorReceiver {
    private long nextAccountNumber = 0;
    public final String bankCode;
    private InterbankPaymentSystemMediator interbankPaymentSystem;
    public Bank(String bankCode) {
        this.bankCode = bankCode;
        this.accountMap = new HashMap<>();
        this.customerMap = new HashMap<>();
        this.operationHistory = new History();
    }
    private Map<String, Account> accountMap; // Key=InBankAccountNumber -> AccountNumber.getInBankAccountNumber()
    private Map<String, Customer> customerMap; // Key=Username
    private History operationHistory;
    public Customer createCustomer(String username, String name, String surname) {
        if (customerMap.containsKey(username)) {
            return null;
        }
        Customer newCustomer = new Customer(name, surname, username);
        customerMap.put(username, newCustomer);
        return newCustomer;
    }
    public Account createAccount(Customer customer, AccountInterestRateStrategy accountInterestRateStrategy) {
        Customer owner;
        if ((owner = customerMap.get(customer.getUsername())) != null) {
            AccountNumber newAccountNumber = getNextAccountNumber();
            Account newAccount = new Account(this, owner, newAccountNumber, accountInterestRateStrategy);
            accountMap.put(newAccountNumber.getInBankAccountNumber(), newAccount);
            return newAccount;
        }
        return null;
    }
    public Customer getCustomer(String username) {
        return customerMap.getOrDefault(username, null);
    }
    public List<Account> getCustomerAccounts(Customer customer) {
        return new ArrayList<>(accountMap.values().stream().filter(acc -> acc.getOwner() == customer).toList());
    }
    public Account getAccountByNumber(AccountNumber accountNumber) {
        if (!accountMap.containsKey(accountNumber.getInBankAccountNumber())) {
            return null;
        }
        return accountMap.get(accountNumber.getInBankAccountNumber());
    }

    public boolean transfer(Account source, AccountNumber destination, double amount, String text) {
        if (!Objects.equals(source.accountNumber.getBankIdentifier(), bankCode)) return false;
        if (source.getBalance() < amount) {
            return false;
            // todo handle debit accounts
        }
        if (Objects.equals(destination.getBankIdentifier(), bankCode)) {
            if (accountMap.containsKey(destination.getInBankAccountNumber())) {
                Account destinationAccount = accountMap.get(destination.getInBankAccountNumber());
                return executeOperation(new InternalTransferCommand(this, source, destinationAccount, amount, text));
            }
            else {
                return false;
                // todo such account does not exist in this bank but is internal transfer
            }
        }
        else if (interbankPaymentSystem != null) {
            return executeOperation(new OutgoingExternalTransferCommand(this, interbankPaymentSystem, source, destination, amount, text));
        }

        // Impossible to process payment. There is not any interbank payment system and the account does not exist here
        return false;
    }
    public boolean payment(Account recipient, double amount) {
        if (recipient == null) return false;
        return recipient.executeOperation(new DirectPaymentCommand(recipient, amount));
    }
    public boolean withdraw(Account target, double amount) {
        if (target == null) return false;
        return target.executeOperation(new WithdrawCommand(target, amount));
    }
    public boolean executeOperation(Command command) {
        command.execute();
        operationHistory.log(command);
        return true;
    }
    public boolean createDeposit(Account account, double depositAmount, DepositInterestRateStrategy depositInterestRateStrategy) {
        if (account.getBalance() < depositAmount) return false;
        account.executeOperation(new CreateDepositCommand(this, account, getNextAccountNumber(), depositAmount, depositInterestRateStrategy));
        return true;
    }
    public boolean closeDeposit(Deposit deposit) {
        deposit.getOwningAccount().executeOperation(new CloseDepositCommand(deposit));
        return true;
    }
    public boolean createLoan(Account account, double borrowAmount, LoanInterestRateStrategy loanInterestRateStrategy) {
        if (borrowAmount < 0) return false;
        account.executeOperation(new CreateLoanCommand(this, account, getNextAccountNumber(), loanInterestRateStrategy, borrowAmount));
        return true;
    }
    public void logOperation(Command command) {
        operationHistory.log(command);
    }
    public void setInterbankPaymentSystem(InterbankPaymentSystemMediator ibp) {
        interbankPaymentSystem = ibp;
    }
    private AccountNumber getNextAccountNumber() {
        AccountNumber newAccountNumber = new AccountNumber(bankCode, String.format("%06d", nextAccountNumber));
        nextAccountNumber += 1;
        return newAccountNumber;
    }
    public List<Account> doAccountReport(AccountVisitor visitor) {
        List<Account> result = new ArrayList<Account>();
        for (Account acc : accountMap.values()) {
            if (acc.accept(visitor) != null) {
                result.add(acc);
            }
        }
        return result;
    }
    public List<Command> doOperationReport(VisitorReceiver reportableObject, HistoryVisitor visitor) {
        return reportableObject.accept(visitor);
    }
    public List<Customer> doCustomerReport(CustomerVisitor visitor) {
        List<Customer> result = new ArrayList<>();
        for (Customer cust : customerMap.values()) {
            if (cust.accept(visitor) != null) {
                result.add(cust);
            }
        }
        return result;
    }

    public List<Loan> getLoansForAccount(Account account) {
        return account.getLoans();
    }
    public List<Deposit> getDepositsForAccount(Account account) {
        return account.getDeposits();
    }
    public boolean repayLoan(Account account, Loan loan, double amount) {
        if (account instanceof Loan || account instanceof Deposit) return false;
        if (Math.abs(loan.getBalance()) < Math.abs(amount)) return false;

        return executeOperation(new InternalTransferCommand(this, account, loan, amount, "REPAY LOAN: " + loan.accountNumber));
    }
    public History getOperationHistory() {
        return operationHistory;
    }

    @Override
    public Account accept(AccountVisitor visitor) {
        return null;
    }
    @Override
    public Customer accept(CustomerVisitor visitor) {
        return null;
    }
    @Override
    public List<Command> accept(HistoryVisitor visitor) {
        return operationHistory.accept(visitor);
    }
}
