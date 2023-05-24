package Bank;

import java.time.LocalDate;
import java.util.*;

public class Bank {
    public final String bankCode;

    public Bank(String bankCode) {
        this.bankCode = bankCode;
        this.accountMap = new HashMap<>();
        this.customerMap = new HashMap<>();
        this.history = new History();
    }
    private Map<String, Account> accountMap;
    private Map<String, Customer> customerMap;
    private History history;
    InterbankMediator interbankMediator;

    // private TransactionExecutor executor;
    // public getTransactionExecutor()

    public enum CreateAccountStatus {
        SUCCESS,
        FAIL
    }

    void setInterbankMediator(InterbankMediator mediator) {
        this.interbankMediator = mediator;
    }

    public Customer registerCustomer(String name, String surname, LocalDate dateOfBirth, LogonData logonData) {
        Customer newCustomer = new Customer(this, name, surname, dateOfBirth, logonData);
        if (newCustomer.isValid() && !customerMap.containsKey(logonData.getUsername())) {
            customerMap.put(logonData.getUsername(), newCustomer);
            return newCustomer;
        }
        return null;
    }

    public CreateAccountStatus openAccount(LogonData credentials) {
        return openAccount(credentials, 0, Currency.getInstance("PLN"));
    }
    public CreateAccountStatus openAccount(LogonData credentials, double startingBalance, Currency currency) {
        if (logIn(credentials) == null) return CreateAccountStatus.FAIL;

        var customer = customerMap.get(credentials.getUsername());
        if (customer != null) {
            Account newAccount = new Account(customer, startingBalance, currency);
            accountMap.put(newAccount.getId(), newAccount);
            return CreateAccountStatus.SUCCESS;
        }
        return CreateAccountStatus.FAIL;
    }

    public List<Account> logIn(LogonData credentials) {
        var customer = customerMap.get(credentials.getUsername());
        if (customer != null && customer.compareLogonData(credentials)) {
            return customer.getAccounts();
        }
        return null;
    }

    List<Account> getCustomerAccounts(final Customer customer) {
        return accountMap.values().stream().filter(acc -> acc.getOwner() == customer).toList();
    }

    boolean executeCommand(Command command) {
        if (command.execute()) {
            history.pushBack(command);
            return true;
        }
        return false;
    }

    boolean receiveExternalPayment(ExternalTransfer transfer) {
        if (transfer.getSourceAccountId().equals(bankCode)) {
            // TODO: 21/05/2023 Transfer as returned money
            return true;
        }
        else if (accountMap.containsKey(transfer.getRecipientAccountId())) {
            accountMap.get(transfer.getRecipientAccountId())
                    .executeCommand(
                            new IncomingTransferCommand(
                                    this,
                                    null,
                                    transfer.getAmount(),
                                    transfer.getCurrency(),
                                    transfer.getRecipientAccountId(),
                                    transfer.getText()
                            )
                    );
            return true;
        }

        return false;
    }

    void informCommandExecutionOnAccount(Command command) {
        history.pushBack(command);
    }

    Account getAccountById(String accountId) {
        return accountMap.getOrDefault(accountId, null);
    }

    public Map<String, Customer> getCustomerMap(){

        return customerMap;
    }
}
