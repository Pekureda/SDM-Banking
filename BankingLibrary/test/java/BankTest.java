import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

import Bank.*;

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
    void createCustomer() {
        String customerName = "John";
        String customerSurname = "Smith";
        LocalDate customerDateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData = new LogonData("username", "password!");
        Customer customer = bank.registerCustomer(customerName, customerSurname, customerDateOfBirth, logonData);

        assertNotEquals(customer, null);
        assertEquals(customer.getName(), customerName);
        assertEquals(customer.getSurname(), customerSurname);
        assertEquals(customer.getDateOfBirth(), customerDateOfBirth);
        // Check logon data?
    }

    @Test
    void createAccount() {
        String customerName = "John";
        String customerSurname = "Smith";
        LocalDate customerDateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData = new LogonData("username", "password!");
        Customer customer = bank.registerCustomer(customerName, customerSurname, customerDateOfBirth, logonData);

        assertNotEquals(customer, null);
        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.openAccount(customer.getCustomerId(), logonData));
        assertEquals(Bank.CreateAccountStatus.FAIL, bank.openAccount(-1, logonData));
    }

    @Test
    void logIn() {
        String customerName = "John";
        String customerSurname = "Smith";
        LocalDate customerDateOfBirth = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData = new LogonData("username", "password!");
        Customer customer = bank.registerCustomer(customerName, customerSurname, customerDateOfBirth, logonData);

        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.openAccount(customer.getCustomerId(), logonData));
        assertNotNull(bank.logIn(logonData));
        assertEquals(1, bank.logIn(logonData).size());
        assertEquals(Bank.CreateAccountStatus.SUCCESS, bank.openAccount(customer.getCustomerId(), logonData));
        assertEquals(2, bank.logIn(logonData).size());
        assertNotEquals(logonData, new LogonData("username", "wrongPassword?"));
        assertNull(bank.logIn(new LogonData("username", "wrongPassword?")));
    }

    @Test
    void internalTransaction() {
        String customerName1 = "John";
        String customerSurname1 = "Smith";
        LocalDate customerDateOfBirth1 = LocalDate.of(2000, Month.JANUARY, 1);
        LogonData logonData1 = new LogonData("username", "password!");

        String customerName2 = "Alice";
        String customerSurname2 = "Wonderful";
        LocalDate customerDateOfBirth2 = LocalDate.of(2000, Month.MARCH, 14);
        LogonData logonData2 = new LogonData("nickname", "password?");

        var customer1 = bank.registerCustomer(customerName1, customerSurname1, customerDateOfBirth1, logonData1);
        var customer2 = bank.registerCustomer(customerName2, customerSurname2, customerDateOfBirth2, logonData2);

        bank.openAccount(customer1.getCustomerId(), logonData1, 1000, Currency.getInstance("PLN"));
        bank.openAccount(customer2.getCustomerId(), logonData2, 0, Currency.getInstance("PLN"));

        var accounts1 = bank.logIn(logonData1);
        assertEquals(1, accounts1.size());
        var account1 = accounts1.get(0);

        var accounts2 = bank.logIn(logonData2);
        assertEquals(1, accounts2.size());
        var account2 = accounts2.get(0);
        var account2Id = account2.getId();

        assertTrue(account1.orderTransfer(500, Currency.getInstance("PLN"), account2Id, "Payment for used goods"));
        assertEquals(500, account1.getBalance());
        assertEquals(500, account2.getBalance());

        assertFalse(account2.orderTransfer(100, Currency.getInstance("PLN"), "abcdefghijk", "Negative example"));

        assertEquals(500, account1.getBalance());
        assertEquals(500, account2.getBalance());
    }
}