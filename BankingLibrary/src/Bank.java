import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    static class LogonData {
        public LogonData(String username, String password) {
            this.username = username;
            this.password = password;
        }
        private final String username;
        private final String password;

        public boolean isValid() {
            return this.username.length() > 0 && !this.username.matches("\s") && this.password.length() > 6 && !this.password.matches("\s");
        }
    }


    public class Account {
        //todo;
    }



    class Customer {
        private static int nextId = 0;
        public Customer(String name, String surname, LocalDate dateOfBirth, LogonData logonData) {
            this.name = name;
            this.surname = surname;
            this.dateOfBirth = dateOfBirth;
            this.logonData = logonData;
            this.customerId = nextId;

            nextId++;
;        }
        private final int customerId;
        private String name;
        private String surname;
        private LocalDate dateOfBirth;
        private LogonData logonData;

        boolean isValid() {
            return !this.name.isBlank() && !this.surname.isBlank() && dateOfBirth.isBefore(LocalDate.now()) && logonData.isValid();
        }

        public String getName() {

            myFatherBank.getMyAccount(this.id);

            return this.name;
        }
        public String getSurname() {
            return this.surname;
        }

        public int getCustomerId() {
            return customerId;
        }

        public List<Account> getAccounts() {
            return accountMap.getOrDefault(customerId, new ArrayList<>());
        }
    }

    Bank(int bankId) {
        this.bankId = bankId;
        accountMap = new HashMap<>();
        customerList = new ArrayList<>();
    }

    private int bankId;
    private Map<Integer, List<Account>> accountMap;
    private List<Customer> customerList;
    private History history;

    public enum CreateAccountStatus {
        SUCCESS,
        FAIL
    }

    public Customer createCustomer(Customer newCustomer) {
        if (newCustomer.isValid()) {
            customerList.add(newCustomer);
            return newCustomer;
        }
        return null;
    }

    public CreateAccountStatus createAccount(int customerId) {
        if (customerList.stream().filter(customer -> customer.customerId == customerId).count() == 1) {
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

    public List<Account> logIn(LogonData logonData) {
        var customer = customerList.stream().filter(elem -> elem.logonData.equals(logonData)).findFirst().orElse(null);
        if (customer != null) {
            return customerList.get(customerList.indexOf(customer)).getAccounts();
        }
        return null;
    }
}
