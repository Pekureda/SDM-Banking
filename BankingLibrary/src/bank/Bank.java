package bank;

import java.time.LocalDate;
import java.util.*;

public class Bank {

    public Bank(int bankId) {
        this.bankId = bankId;
        accountMap = new HashMap<>();
        customerList = new ArrayList<>();
    }

    private int bankId;
    private Map<Integer, List<Account>> accountMap;
    private List<Customer> customerList;
    //private History history;

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

    public CreateAccountStatus openAccount(int customerId) {
        if (customerList.stream().filter(customer -> customer.getCustomerId() == customerId).count() == 1) {
            if (accountMap.containsKey(customerId)) {
                accountMap.get(customerId).add(new Account());
            }
            else {
                accountMap.put(customerId, new ArrayList<>(List.of(new Account())));
            }

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

    List<Account> getCustomerAccountsById(Integer customerId) {
        return accountMap.getOrDefault(customerId, new ArrayList<>());
    }
}
