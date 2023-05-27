import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bank.*;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank("0123");
    }

    @AfterEach
    void tearDown() {
        bank = null;
    }

    @Test
    void basicCreateCustomer() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";
        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);

        assertNotNull(customer);
        assertEquals(customer.getName(), customerName);
        assertEquals(customer.getSurname(), customerSurname);
        assertEquals(customer.getUsername(), customerUsername);

        String otherCustomerName = "Bob";
        String otherCustomerSurname = "Anvil";
        String otherCustomerUsername = "hoshiFan";

        assertNull(bank.createCustomer(customerUsername, otherCustomerName, otherCustomerSurname));

        Customer otherCustomer = bank.createCustomer(otherCustomerUsername, otherCustomerName, otherCustomerSurname);

        assertNotNull(otherCustomer);
        assertEquals(otherCustomer.getName(), otherCustomerName);
        assertEquals(otherCustomer.getSurname(), otherCustomerSurname);
        assertEquals(otherCustomer.getUsername(), otherCustomerUsername);

        assertNotEquals(customer, otherCustomer);
        assertEquals(bank.getCustomer(customerUsername), customer);
        assertEquals(bank.getCustomer(otherCustomerUsername), otherCustomer);
    }

    @Test
    void createAccount() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";

        String otherCustomerName = "Bob";
        String otherCustomerSurname = "Anvil";
        String otherCustomerUsername = "hoshiFan";
        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);
        Customer otherCustomer = bank.createCustomer(otherCustomerUsername, otherCustomerName, otherCustomerSurname);

        assertNotNull(customer);
        assertNotNull(otherCustomer);
        assertNotEquals(customer, otherCustomer);

        for (int i = 1; i < 10; i++) {
            Account customerAccount = bank.createAccount(customer);
            Account otherCustomerAccount = bank.createAccount(otherCustomer);

            assertNotEquals(customerAccount, otherCustomerAccount);
            assertEquals(bank.getCustomerAccounts(customer).size(), i);
            assertEquals(bank.getCustomerAccounts(otherCustomer).size(), i);

            assertNotNull(bank.getAccountByNumber(customerAccount.accountNumber));
            assertNotNull(bank.getAccountByNumber(otherCustomerAccount.accountNumber));

            assertEquals(customerAccount, bank.getAccountByNumber(customerAccount.accountNumber));
            assertEquals(otherCustomerAccount, bank.getAccountByNumber(otherCustomerAccount.accountNumber));
        }
    }

    @Test
    void internalTransaction() {
        String customerName = "John";
        String customerSurname = "Smith";
        String customerUsername = "majkel1";

        String otherCustomerName = "Bob";
        String otherCustomerSurname = "Anvil";
        String otherCustomerUsername = "hoshiFan";

        Customer customer = bank.createCustomer(customerUsername, customerName, customerSurname);
        Customer otherCustomer = bank.createCustomer(otherCustomerUsername, otherCustomerName, otherCustomerSurname);

        Account customerAccount = bank.createAccount(customer);
        var customerAccountNumber = customerAccount.accountNumber;
        assertEquals(customerAccountNumber.toString(), bank.bankCode + String.format("%06d", 0));
        Account otherCustomerAccount = bank.createAccount(otherCustomer);
        var otherCustomerAccountNumber = otherCustomerAccount.accountNumber;
        assertEquals(otherCustomerAccountNumber.toString(), bank.bankCode + String.format("%06d", 1));

        var customerAccounts = bank.getCustomerAccounts(customer);
        assertEquals(1, customerAccounts.size());
        assertEquals(customerAccounts.get(0), customerAccount);

        var otherCustomerAccounts = bank.getCustomerAccounts(otherCustomer);
        assertEquals(1, otherCustomerAccounts.size());
        assertEquals(otherCustomerAccounts.get(0), otherCustomerAccount);

        double amountPaid = 500;
        assertTrue(bank.payment(customerAccount, amountPaid));
        assertEquals(customerAccount.getBalance(), amountPaid);

        assertTrue(bank.transfer(customerAccount, otherCustomerAccountNumber, 250, "Too much"));
        assertEquals(250, otherCustomerAccount.getBalance());
        assertEquals(250, customerAccount.getBalance());
        assertTrue(bank.transfer(otherCustomerAccount, customerAccountNumber, 100, "Tip"));
        assertEquals(150, otherCustomerAccount.getBalance());
        assertEquals(350, customerAccount.getBalance());

        assertTrue(bank.withdraw(customerAccount, 200));
        assertEquals(150, customerAccount.getBalance());

        assertFalse(bank.withdraw(otherCustomerAccount, 1000));
        assertEquals(150, otherCustomerAccount.getBalance());
    }
}