package Bank;

import Bank.Commands.*;

import java.util.*;

public class Bank implements OperationExecutor {
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
    public Account createAccount(Customer customer) { // todo add interest rate
        Customer owner;
        if ((owner = customerMap.get(customer.getUsername())) != null) {
            AccountNumber newAccountNumber = getNextAccountNumber();
            nextAccountNumber++;
            Account newAccount = new Account(this, owner, newAccountNumber);
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
        if (source.getBalance() < amount) {
            return false;
            // todo handle debit accounts
        }
        if (Objects.equals(destination.getBankIdentifier(), bankCode)) {
            if (accountMap.containsKey(destination.getInBankAccountNumber())) {
                Account destinationAccount = accountMap.get(destination.getInBankAccountNumber());
                executeOperation(new InternalTransferCommand(this, source, destinationAccount, amount, text));
                return true;
            }
            else {
                return false;
                // todo such account does not exist in this bank but is internal transfer
            }
        }
        else {
            executeOperation(new OutgoingExternalTransferCommand(this, interbankPaymentSystem, source, destination, amount, text));
        }

        // Should not reach this point
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
    public boolean createDeposit(Account account, double depositAmount) {
        if (account.getBalance() < depositAmount) return false;
        account.executeOperation(new CreateDepositCommand(this, account, getNextAccountNumber(), depositAmount));
        return true;
    }
    public boolean createLoan(Account account, double borrowAmount) {
        if (borrowAmount < 0) return false;
        account.executeOperation(new CreateLoanCommand(this, account, getNextAccountNumber(), borrowAmount));
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
}
