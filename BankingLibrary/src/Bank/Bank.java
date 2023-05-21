package Bank;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    public final String bankCode;

    public Bank(String bankCode) {
        this.bankCode = bankCode;
        accountMap = new HashMap<>();
        customerList = new ArrayList<>();
    }
    private Map<String, Account> accountMap;
    private List<Customer> customerList;
    //private Bank.History history;

    // private TransactionExecutor executor;
    // public getTransactionExecutor()

    public enum CreateAccountStatus {
        SUCCESS,
        FAIL
    }

    public Customer registerCustomer(String name, String surname, LocalDate dateOfBirth, LogonData logonData) {
        Customer newCustomer = new Customer(this, name, surname, dateOfBirth, logonData);
        if (newCustomer.isValid()) {
            customerList.add(newCustomer);
            return newCustomer;
        }
        return null;
    }

    public CreateAccountStatus openAccount(int customerId, LogonData credentials) {
        return openAccount(customerId, credentials, 0, Currency.getInstance("PLN"));
    }
    public CreateAccountStatus openAccount(int customerId, LogonData credentials, double startingBalance, Currency currency) {
        if (logIn(credentials) == null) return CreateAccountStatus.FAIL;

        var customer = customerList.stream().filter(c -> c.getCustomerId() == customerId).toList();
        if (customer.size() == 1) {
            Account newAccount = new Account(customer.get(0), startingBalance, currency);
            accountMap.put(newAccount.getId(), newAccount);
            return CreateAccountStatus.SUCCESS;
        }
        return CreateAccountStatus.FAIL;
    }

    public List<Account> logIn(LogonData credentials) {
        var customer = customerList.stream().filter(elem -> elem.compareLogonData(credentials)).findFirst().orElse(null);
        if (customer != null) {
            return customerList.get(customerList.indexOf(customer)).getAccounts();
        }
        return null;
    }

    public List<Account> getCustomerAccounts(final Customer customer) {
        return accountMap.values().stream().filter(acc -> acc.getOwner() == customer).toList();
    }

    boolean executeCommand(Command command) {
        if (command.execute()) {
            // TODO: 21/05/2023 Put into history
            return true;
        }
        return false;
    }

    Account getAccountById(String accountId) {
        return accountMap.getOrDefault(accountId, null);
    }
}
