package Bank;

import Bank.Commands.Command;
import Bank.Commands.InternalTransferCommand;

import java.util.*;

public class Bank {
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
        return customerMap.put(username, new Customer(name, surname, username));
    }
    public Account createAccount(Customer customer) {
        Customer owner;
        if ((owner = customerMap.get(customer.getUsername())) != null) {
            AccountNumber newAccountNumber = new AccountNumber(bankCode, String.format("%010d", nextAccountNumber));
            nextAccountNumber++;
            return accountMap.put(newAccountNumber.getInBankAccountNumber(), new Account(this, owner, newAccountNumber));
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
            }
            else {
                return false;
                // todo such account does not exist in this bank but is internal transfer
            }
        }
        else {
            // todo this is external transfer
        }

        // Should not reach this point
        return false;
    }

    public boolean executeOperation(Command command) {
        command.execute();
        operationHistory.log(command);
        return true;
    }
    public void logOperation(Command command) {
        operationHistory.log(command);
    }
    public void setInterbankPaymentSystem(InterbankPaymentSystemMediator ibp) {
        interbankPaymentSystem = ibp;
    }
}
